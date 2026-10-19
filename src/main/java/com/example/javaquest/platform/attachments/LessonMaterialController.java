package com.example.javaquest.platform.attachments;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestController
class LessonMaterialController {
    private static final String BASE="/api/chapters/{chapter}/lessons/{lesson}";
    record LinkRequest(String title,String url) {}
    private final LessonMaterialService service;
    private final AttachmentStorage storage;
    LessonMaterialController(LessonMaterialService service,AttachmentStorage storage) {
        this.service=service; this.storage=storage;
    }
    @GetMapping(BASE+"/materials")
    List<LessonMaterialService.View> list(Authentication auth,@PathVariable String chapter,@PathVariable String lesson) {
        return service.list(auth.getName(),chapter,lesson);
    }
    @PostMapping(BASE+"/links") @ResponseStatus(HttpStatus.CREATED)
    LessonMaterialService.View add(Authentication auth,@PathVariable String chapter,@PathVariable String lesson,
                                    @RequestBody LinkRequest body) {
        return service.addLink(auth.getName(),chapter,lesson,body.title(),body.url());
    }
    @PutMapping(BASE+"/links/{id}")
    LessonMaterialService.View edit(Authentication auth,@PathVariable String chapter,@PathVariable String lesson,
                                     @PathVariable UUID id,@RequestBody LinkRequest body) {
        return service.editLink(auth.getName(),chapter,lesson,id,body.title(),body.url());
    }
    @DeleteMapping(BASE+"/links/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteLink(Authentication auth,@PathVariable String chapter,@PathVariable String lesson,@PathVariable UUID id) {
        service.delete(auth.getName(),chapter,lesson,id,LessonMaterial.Kind.LINK);
    }
    @PostMapping(value=BASE+"/attachments",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    LessonMaterialService.View upload(Authentication auth,@PathVariable String chapter,@PathVariable String lesson,
                                       @RequestParam("file") MultipartFile file) throws IOException {
        return service.upload(auth.getName(),chapter,lesson,file);
    }
    @DeleteMapping(BASE+"/attachments/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteFile(Authentication auth,@PathVariable String chapter,@PathVariable String lesson,@PathVariable UUID id) {
        service.delete(auth.getName(),chapter,lesson,id,LessonMaterial.Kind.ATTACHMENT);
    }
    @GetMapping("/api/attachments/{id}")
    ResponseEntity<Resource> download(Authentication auth,@PathVariable UUID id) {
        LessonMaterial item=service.download(auth.getName(),id);
        Resource resource=new FileSystemResource(storage.readable(id));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,ContentDisposition.attachment()
                        .filename(item.title,StandardCharsets.UTF_8).build().toString())
                .header(HttpHeaders.CACHE_CONTROL,"private, no-store")
                .header("X-Content-Type-Options","nosniff").body(resource);
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    ResponseEntity<java.util.Map<String,String>> oversized() {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(java.util.Map.of("message","Plik przekracza dozwolony rozmiar."));
    }
    @ExceptionHandler(org.springframework.web.server.ResponseStatusException.class)
    ResponseEntity<java.util.Map<String,String>> invalid(org.springframework.web.server.ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(java.util.Map.of("message",
                e.getReason()==null?"Nie można wykonać operacji.":e.getReason()));
    }
    @ExceptionHandler(IOException.class)
    ResponseEntity<java.util.Map<String,String>> storageError() {
        return ResponseEntity.internalServerError().body(java.util.Map.of("message","Nie można zapisać pliku. Spróbuj ponownie."));
    }
}

