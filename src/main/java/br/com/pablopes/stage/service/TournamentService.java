package br.com.pablopes.stage.service;

import br.com.pablopes.stage.api.model.input.TournamentIModel;
import br.com.pablopes.stage.api.model.output.TournamentOModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface TournamentService {
    TournamentOModel create(TournamentIModel tournament);

}
