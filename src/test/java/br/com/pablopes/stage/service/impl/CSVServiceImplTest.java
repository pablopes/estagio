package br.com.pablopes.stage.service.impl;

import br.com.pablopes.stage.core.validation.CustomMultipartFile;
import br.com.pablopes.stage.domain.model.Tournament;
import br.com.pablopes.stage.domain.model.Team;
import br.com.pablopes.stage.service.CSVService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CSVServiceImplTest {

    private CSVServiceImpl<Team> csvService;
    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        csvService = new CSVServiceImpl<>();
        byte[] csvData = "id;name;place\n1;Team A;City X\n2;Team B;City Y".getBytes(StandardCharsets.UTF_8);
        mockFile = new CustomMultipartFile(csvData);
    }

    @Test
    void testReadCSVFile() {
        List<Team> teams = csvService.read(mockFile, Team.class);
        assertNotNull(teams);
        assertEquals(2, teams.size());
        assertEquals("Team A", teams.get(0).getName());
        assertEquals("Team B", teams.get(1).getName());
    }

    @Test
    void testReadCSVToMap() {
        byte[] csvData = "Team;City X;City Y\nA;10;20\nB;5;15".getBytes(StandardCharsets.UTF_8);
        MultipartFile matrixFile = new CustomMultipartFile(csvData);
        Map<String, Map<String, Double>> resultMap = csvService.read(matrixFile);

        assertNotNull(resultMap);
        assertEquals(2, resultMap.size());
        assertTrue(resultMap.containsKey("A"));
        assertTrue(resultMap.get("A").containsKey("City X"));
        assertEquals(10.0, resultMap.get("A").get("City X"));
    }

}
