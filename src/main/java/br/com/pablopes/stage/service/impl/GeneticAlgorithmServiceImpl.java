package br.com.pablopes.stage.service.impl;

import br.com.pablopes.stage.api.model.input.TournamentIModel;
import br.com.pablopes.stage.api.model.output.TournamentOModel;
import br.com.pablopes.stage.domain.model.Team;
import br.com.pablopes.stage.domain.model.Tournament;
import br.com.pablopes.stage.service.CSVService;
import br.com.pablopes.stage.service.TournamentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class GeneticAlgorithmServiceImpl implements TournamentService {
    private final CSVService csvService;

    public GeneticAlgorithmServiceImpl(CSVService csvService) {
        this.csvService = csvService;
    }

    @Override
    public TournamentOModel create(TournamentIModel tournament) {
        var bestTournament = new AtomicReference<>(new Tournament());
        var matrizDistance = this.csvService.read(tournament.getMatrizFile());

        //INICIALIZAÇÃO DA POPULAÇAO
        var population = new AtomicReference<>(this.startingPopulation(tournament.getTeamsFile(), 1000));
        //AVALIAÇÃO DA POPULAÇÃO
        evaluation(population.get(), matrizDistance);
        //MELHOR SOLUÇÃO
        bestTournament.set(bestTournament(population, bestTournament.get()));

        //CRIANDO GERAÇÕES
        IntStream.range(0, 100).forEach(iterator -> {
            System.out.println("Geração: " + iterator);

            var newPopulation = Stream.generate(() -> {
                //SELEÇÃO DOS PAIS
                Tournament father = this.roulette(population.get());
                //RECOMBINAÇÃO
                Tournament son = this.crossover(father);
                //MUTAÇÃO
                this.mutation(son);

                return son;
            }).limit(1000).toList();

            //SOBRESCREVENDO POPULAÇÃO ANTERIOR
            population.set(new ArrayList<>(0));
            population.get().addAll(newPopulation);

            //AVALIAÇÃO DA POPULAÇÃO
            evaluation(population.get(), matrizDistance);
            //MELHOR SOLUÇÃO
            bestTournament.set(bestTournament(population, bestTournament.get()));

        });

        return new TournamentOModel(bestTournament.get().getRounds());

    }

    private void mutation(Tournament son) {
        if ((Math.random() * 101) < 5) {
            var roundX = ThreadLocalRandom.current().nextInt(0, (son.getRounds().size()));
            var roundY = ThreadLocalRandom.current().nextInt(0, (son.getRounds().size()));
            Collections.swap(son.getRounds(), roundX, roundY);
        }
    }

    private Tournament crossover(Tournament father) {
        Tournament son = father;
        int begin = ThreadLocalRandom.current().nextInt(0, father.getRounds().size());
        int end = ThreadLocalRandom.current().nextInt(begin, father.getRounds().size() + 1);
        Collections.shuffle(son.getRounds().subList(begin, end));

        return son;
    }

    private Tournament roulette(List<Tournament> population) {
        var limit = Math.random() * sumRating(population);
        var sum = 0.0;
        int i;
        for (i = 0; i < population.size() && sum < limit; i++) {
            sum += population.get(i).getRating();
        }
        return population.get(i > 0 ? i - 1 : 0);
    }

    private double sumRating(List<Tournament> populations) {
        AtomicReference<Double> rating = new AtomicReference<>(0.0);
        populations.forEach(p -> {
            rating.updateAndGet(r -> r + p.getRating());
        });
        return rating.get();
    }

    private Tournament bestTournament(AtomicReference<List<Tournament>> population, Tournament thebest) {
        return Stream.of(thebest, population.get().stream().max(Comparator.comparingDouble(Tournament::getRating)).get())
                .max(Comparator.comparingDouble(Tournament::getRating)).orElseThrow();
    }

    private List<Tournament> startingPopulation(MultipartFile file, int population) {
        var teams = csvService.read(file, Team.class);
        var template = csvService.createTournament(teams);
        return Stream.generate(() -> {
            var firstTeam = this.random(template);
            var secondTeam = this.random(template);
            var tournament = new Tournament();
            var rounds = template.getRounds().stream()
                    .map(round -> {
                        round.getMatches().stream().map(match -> {
                            if (match.getHome().getName().equals(firstTeam.getName())) {
                                match.setHome(secondTeam);
                            } else if (match.getHome().getName().equals(secondTeam.getName())) {
                                match.setHome(firstTeam);
                            }

                            if (match.getAway().getName().equals(firstTeam.getName())) {
                                match.setAway(secondTeam);
                            } else if (match.getAway().getName().equals(secondTeam.getName())) {
                                match.setAway(firstTeam);
                            }
                            return match;
                        }).collect(Collectors.toList());
                        return round;
                    })
                    .collect(Collectors.toList());

            tournament.setRounds(rounds);
            return tournament;
        }).limit(population).toList();
    }

    private Team random(Tournament template) {
        Random random = new Random();
        var round = template
                .getRounds()
                .get(random.nextInt(0, 18));

        var match =
                round.getMatches()
                        .get(random.nextInt(0, 9));

        return random.nextInt(0, 1) == 0
                ? match.getHome()
                : match.getAway();
    }

    private void evaluation(List<Tournament> population, Map<String, Map<String, Double>> matrizDistance) {
        population.stream().parallel().forEach(
                p -> {
                    this.calculateDistance(p, matrizDistance);
                    this.calculateRating(p);
                }
        );
    }

    private void calculateDistance(Tournament tournament, Map<String, Map<String, Double>> matriz) {
        AtomicReference<Integer> distance = new AtomicReference<>(0);
        tournament.getRounds().stream().map(round ->
                round.getMatches().stream().map(
                        match -> {
                            distance.updateAndGet(v -> (int) (v + matriz.get(match.getAway().getCurrentPlace()).get(match.getHome().getPlace())));
                            if (!match.getHome().getPlace().equals(match.getHome().getCurrentPlace())) {
                                distance.updateAndGet(v -> (int) (v + matriz.get(match.getAway().getCurrentPlace()).get(match.getHome().getPlace())));
                                match.getHome().setCurrentPlace(match.getHome().getPlace());
                            }
                            match.getAway().setCurrentPlace(match.getHome().getPlace());
                            return match;
                        }).collect(Collectors.toList())
        ).collect(Collectors.toList());

        tournament.setDistance(distance.get());
    }

    private void calculateRating(Tournament tournament) {
        var homeSequence = 0;
        var awaySequence = 0;
        AtomicInteger total = new AtomicInteger();
        var teamHome = new ArrayList<Team>();
        var teamAway = new ArrayList<Team>();

        var teams = new HashMap<String, Map<String, Integer>>();
        tournament.getRounds().stream().map(round -> {
            round.getMatches().stream().map(match -> {

                if (!teams.containsKey(match.getHome().getName())) {
                    var value = new HashMap<String, Integer>();
                    value.put("home", 1);
                    teams.put(match.getHome().getName(), value);
                } else {
                    if (!teams.get(match.getHome().getName()).containsKey("home")) {
                        teams.get(match.getHome().getName()).remove("away");
                        teams.get(match.getHome().getName()).put("home", 0);
                    }
                    teams.get(match.getHome().getName()).put("home", teams.get(match.getHome().getName()).get("home") + 1);

                    if (teams.get(match.getHome().getName()).get("home") >= 3) {
                        total.addAndGet(1);
                    }
                }

                if (!teams.containsKey(match.getAway().getName())) {
                    var value = new HashMap<String, Integer>();
                    value.put("away", 1);
                    teams.put(match.getAway().getName(), value);
                } else {
                    if (!teams.get(match.getAway().getName()).containsKey("away")) {
                        teams.get(match.getAway().getName()).remove("home");
                        teams.get(match.getAway().getName()).put("away", 0);
                    }
                    teams.get(match.getAway().getName()).put("away", teams.get(match.getAway().getName()).get("away") + 1);
                    if (teams.get(match.getAway().getName()).get("away") >= 3) {
                        total.addAndGet(1);
                    }
                }

                return match;
            }).collect(Collectors.toList());
            return round;
        }).collect(Collectors.toList());

        var rating = 1.0 / (tournament.getDistance() + ((tournament.getDistance() / 100.0) * total.get()));
        tournament.setRating(rating);
        tournament.setPenalty(total.get());
        total.set(0);

    }


}
