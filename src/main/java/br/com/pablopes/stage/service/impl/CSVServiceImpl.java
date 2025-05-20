package br.com.pablopes.stage.service.impl;

import br.com.pablopes.stage.core.validation.CustomMultipartFile;
import br.com.pablopes.stage.domain.model.*;
import br.com.pablopes.stage.service.CSVService;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.dsig.spec.HMACParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CSVServiceImpl<T> implements CSVService {

    @Override
    public List<T> read(MultipartFile file, Class clazz) {
        try {
            CsvToBeanBuilder<T> beanBuilder = new CsvToBeanBuilder<>(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            return beanBuilder.withType(clazz)
                    .withSkipLines(1)
                    .withIgnoreEmptyLine(true)
                    .withSeparator(';')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Map<String, Double>> read(MultipartFile file) {

        try(BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))){
            String line;
            List<String> headers = null;
            String column;
            Map<String, Map<String, Double>> map = new HashMap<>();

            while ((line = br.readLine()) != null){

                if(headers == null){
                    headers = Arrays.asList(line.split(";"));
                }else {
                    var list = Arrays.asList(line.split(";"));
                    Map<String, Double> value = new HashMap<String, Double>();
                    AtomicInteger i = new AtomicInteger(1);
                    var key = list.get(0);



                    List<String> finalHeaders = headers;
                    list.stream().skip(1).forEach(item -> {
                        value.put(finalHeaders.get(i.get()), Double.valueOf(item));
                        i.getAndIncrement();

                    });
                    map.put(key, value);
                }

            }
            return map;
        }catch (IOException e){
            throw  new RuntimeException();
            }

    }

    @Override
    public Tournament createTournament(List teams) {
        try {
            var tournament = new Tournament();
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("planilhas/template.CSV");

            var file = new CustomMultipartFile(inputStream.readAllBytes());
            var template = (List<Template>) this.read(file, Template.class);

            var round = new Round();
            for(Template team : template){
                var home = (Team) teams.get(team.getHome());
                var away = (Team) teams.get(team.getAway());
                var match = new Match(home, away);
                if(round.getMatches().size() == 10){
                    tournament.getRounds().add(round);
                    round = new Round();
                }
                round.getMatches().add(match);
            }
            tournament.getRounds().add(round);

            return tournament;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }


}
