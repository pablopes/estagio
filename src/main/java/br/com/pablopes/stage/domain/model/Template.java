package br.com.pablopes.stage.domain.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Template {
    @CsvBindByPosition(position = 0)
    private int home;
    @CsvBindByPosition(position = 1)
    private int away;
}
