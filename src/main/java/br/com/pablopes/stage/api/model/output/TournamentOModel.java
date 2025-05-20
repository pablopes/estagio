package br.com.pablopes.stage.api.model.output;

import br.com.pablopes.stage.domain.model.Round;
import br.com.pablopes.stage.domain.model.Tournament;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TournamentOModel {
    private List<Round> rounds = new ArrayList<>(0);
}
