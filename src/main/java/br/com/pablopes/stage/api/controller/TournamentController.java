package br.com.pablopes.stage.api.controller;


import br.com.pablopes.stage.api.model.input.TournamentIModel;
import br.com.pablopes.stage.api.model.output.TournamentOModel;
import br.com.pablopes.stage.service.TournamentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tournament")
@Tag(name = "Team Controller", description = "Gerencia as equipes")
public class TournamentController {
    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @Operation(summary = "Cria tabelas de campeonato", description = "Retorna uma tabela completa")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TournamentOModel create(@Valid TournamentIModel tournamentIModel){
        return tournamentService.create(tournamentIModel);
    }
}
