package br.com.pablopes.stage.core.validation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;


@AllArgsConstructor
@NoArgsConstructor
public class CustomMultipartFile implements MultipartFile {
    private byte[] input;

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getOriginalFilename() {
        return "";
    }

    @Override
    public String getContentType() {
        return "";
    }

    @Override
    public boolean isEmpty() {
        return this.input == null || this.input.length == 0;
    }

    @Override
    public long getSize() {
        return input.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return input;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(input);
    }


    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try(var fos = new FileOutputStream(dest)){
            fos.write(this.input);
        }
    }

    @Override
    public void transferTo(Path dest) throws IOException, IllegalStateException {
        MultipartFile.super.transferTo(dest);
    }
}
