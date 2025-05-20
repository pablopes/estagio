package br.com.pablopes.stage.core.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileContentTypeValidatorTest {

    private FileContentTypeValidator validator;
    private MultipartFile mockFile;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new FileContentTypeValidator();
        context = mock(ConstraintValidatorContext.class);
        mockFile = mock(MultipartFile.class);

        // Simulando tipos de arquivos permitidos
        FileContentType constraint = mock(FileContentType.class);
        when(constraint.allowed()).thenReturn(new String[]{"text/csv", "application/json"});

        validator.initialize(constraint);
    }

    @Test
    void testValidContentType() {
        when(mockFile.getContentType()).thenReturn("text/csv");

        assertTrue(validator.isValid(mockFile, context));
    }

    @Test
    void testInvalidContentType() {
        when(mockFile.getContentType()).thenReturn("image/png");

        assertFalse(validator.isValid(mockFile, context));
    }

    @Test
    void testNullFile() {
        assertTrue(validator.isValid(null, context));
    }

    @Test
    void testValidJsonFile() {
        when(mockFile.getContentType()).thenReturn("application/json");

        assertTrue(validator.isValid(mockFile, context));
    }

    @Test
    void testEmptyContentType() {
        when(mockFile.getContentType()).thenReturn("");

        assertFalse(validator.isValid(mockFile, context));
    }
}
