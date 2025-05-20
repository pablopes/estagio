package br.com.pablopes.stage.service;

import br.com.pablopes.stage.domain.model.Team;
import br.com.pablopes.stage.domain.model.Tournament;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CSVService<T> {
    List<T> read(MultipartFile file, Class<T> clazz);
    Map<String, Map<String, Double>> read(MultipartFile file);
    Tournament createTournament(List<T> teams);
}
