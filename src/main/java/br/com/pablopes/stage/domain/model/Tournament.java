package br.com.pablopes.stage.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {
    private List<Round> rounds = new ArrayList<>(0);
    private Integer distance = 0;
    private Double rating = 0.0;
    private Integer penalty = 0;
}
