package br.com.pablopes.stage.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Round {
    List<Match> matches = new ArrayList<>(0);
}
