package com.example.javaquest.platform.attachments;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Component
class AttachmentStorage {
    private final Path root;
    private final long maxBytes;
    AttachmentStorage(@Value("${platform.attachments.directory:./attachments}") String directory,
                      @Value("${platform.attachments.max-bytes:10485760}") long maxBytes) throws IOException {
        if (maxBytes < 1) throw new IllegalArgumentException("Attachment limit must be positive");
        Path configured=Path.of(directory).toAbsolutePath().normalize();
        Files.createDirectories(configured);
        root=configured.toRealPath();
        this.maxBytes=maxBytes;
    }
    Path path(UUID id) { return root.resolve(id.toString()); }
    void save(UUID id, MultipartFile file) throws IOException {
        if(file.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wybierz niepusty plik.");
        if(file.getSize()>maxBytes) throw tooLarge();
        Path target=path(id);
        boolean created=false;
        try(InputStream input=file.getInputStream()) {
            try(OutputStream output=Files.newOutputStream(target, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
                created=true;
                byte[] buffer=new byte[8192];
                long total=0;
                int length;
                while((length=input.read(buffer))!=-1) {
                    total+=length;
                    if(total>maxBytes) throw tooLarge();
                    output.write(buffer,0,length);
                }
            }
        } catch(IOException|RuntimeException e) {
            if(created) Files.deleteIfExists(target);
            throw e;
        }
    }
    Path readable(UUID id) {
        Path path=path(id);
        if(!Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie ma takiego załącznika.");
        return path;
    }
    void delete(UUID id) throws IOException { Files.deleteIfExists(path(id)); }
    private ResponseStatusException tooLarge() {
        return new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "Plik przekracza dozwolony rozmiar.");
    }
}

