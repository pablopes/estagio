package br.com.pablopes.stage.core.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.io.*;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CustomMultipartFileTest {

    private CustomMultipartFile customMultipartFile;
    private byte[] sampleData;

    @BeforeEach
    void setUp() {
        sampleData = "Teste de arquivo".getBytes();
        customMultipartFile = new CustomMultipartFile(sampleData);
    }

    @Test
    void testGetName() {
        assertEquals("", customMultipartFile.getName());
    }

    @Test
    void testGetOriginalFilename() {
        assertEquals("", customMultipartFile.getOriginalFilename());
    }

    @Test
    void testGetContentType() {
        assertEquals("", customMultipartFile.getContentType());
    }

    @Test
    void testIsEmptyWhenNotEmpty() {
        assertFalse(customMultipartFile.isEmpty());
    }

    @Test
    void testIsEmptyWhenEmpty() {
        CustomMultipartFile emptyFile = new CustomMultipartFile(new byte[0]);
        assertTrue(emptyFile.isEmpty());
    }

    @Test
    void testGetSize() {
        assertEquals(sampleData.length, customMultipartFile.getSize());
    }

    @Test
    void testGetBytes() throws IOException {
        assertArrayEquals(sampleData, customMultipartFile.getBytes());
    }

    @Test
    void testGetInputStream() throws IOException {
        InputStream inputStream = customMultipartFile.getInputStream();
        assertNotNull(inputStream);

        byte[] buffer = new byte[sampleData.length];
        assertEquals(sampleData.length, inputStream.read(buffer));
        assertArrayEquals(sampleData, buffer);
    }

    @Test
    void testTransferToFile() throws IOException {
        File tempFile = File.createTempFile("test", ".txt");
        customMultipartFile.transferTo(tempFile);

        assertTrue(tempFile.exists());
        assertEquals(sampleData.length, tempFile.length());

        // Cleanup
        tempFile.delete();
    }

    @Test
    void testTransferToPath() throws IOException {
        Path tempPath = File.createTempFile("test", ".txt").toPath();
        customMultipartFile.transferTo(tempPath);

        assertTrue(tempPath.toFile().exists());
    }
}
