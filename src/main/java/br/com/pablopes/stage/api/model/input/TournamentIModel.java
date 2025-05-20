package br.com.pablopes.stage.api.model.input;

import br.com.pablopes.stage.core.validation.FileContentType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class TournamentIModel {
    @NotNull
    @FileContentType(allowed = { "text/csv" })
    private MultipartFile teamsFile;
    @NotNull
    @FileContentType(allowed = { "text/csv" })
    private MultipartFile matrizFile;
}
