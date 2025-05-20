package br.com.pablopes.stage.api.controller;

import br.com.pablopes.stage.api.model.input.TournamentIModel;
import br.com.pablopes.stage.api.model.output.TournamentOModel;
import br.com.pablopes.stage.service.TournamentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TournamentControllerTest {
    @Mock
    private TournamentService service;
    @InjectMocks
    private TournamentController controller;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSuccess(){

        Mockito.when(service.create(Mockito.any())).thenReturn(new TournamentOModel(new ArrayList<>()));
        var response = controller.create(new TournamentIModel());

        assertNotNull(response);
    }

}