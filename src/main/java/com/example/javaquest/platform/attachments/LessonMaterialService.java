package com.example.javaquest.platform.attachments;

import com.example.javaquest.platform.chapter.Lesson;
import com.example.javaquest.platform.chapter.LessonRepository;
import com.example.javaquest.platform.user.User;
import com.example.javaquest.platform.user.UserRepository;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
class LessonMaterialService {
    record View(UUID id, String kind, String title, String url) {}
    private static final Logger log=LoggerFactory.getLogger(LessonMaterialService.class);
    private final LessonMaterialRepository materials;
    private final LessonRepository lessons;
    private final UserRepository users;
    private final AttachmentStorage storage;
    LessonMaterialService(LessonMaterialRepository materials, LessonRepository lessons,
                          UserRepository users, AttachmentStorage storage) {
        this.materials=materials; this.lessons=lessons; this.users=users; this.storage=storage;
    }
    @Transactional(readOnly=true)
    List<View> list(String email,String chapter,String slug) {
        return materials.findByUserIdAndLessonIdOrderByTitleAscIdAsc(user(email).getId(),lesson(chapter,slug).getId())
                .stream().map(this::view).toList();
    }
    View addLink(String email,String chapter,String slug,String title,String url) {
        return view(materials.save(new LessonMaterial(UUID.randomUUID(),user(email),lesson(chapter,slug),
                LessonMaterial.Kind.LINK,title(title),link(url))));
    }
    View editLink(String email,String chapter,String slug,UUID id,String title,String url) {
        LessonMaterial item=owned(email,chapter,slug,id,LessonMaterial.Kind.LINK);
        item.title=title(title); item.url=link(url);
        return view(item);
    }
    View upload(String email,String chapter,String slug,MultipartFile file) throws IOException {
        User owner=user(email);
        Lesson lesson=lesson(chapter,slug);
        UUID id=UUID.randomUUID();
        String name=file.getOriginalFilename();
        name=name==null?"załącznik":name.replace('\\','/');
        name=name.substring(name.lastIndexOf('/')+1).replaceAll("[\\p{Cntrl}]","");
        if(name.isBlank())name="załącznik";
        if(name.length()>200)name=name.substring(0,200);
        storage.save(id,file);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override public void afterCompletion(int status) {
                if(status!=STATUS_COMMITTED) removeFile(id);
            }
        });
        return view(materials.saveAndFlush(new LessonMaterial(id,owner,lesson,
                LessonMaterial.Kind.ATTACHMENT,name,"/api/attachments/"+id)));
    }
    void delete(String email,String chapter,String slug,UUID id,LessonMaterial.Kind kind) {
        LessonMaterial item=owned(email,chapter,slug,id,kind);
        materials.delete(item);
        if(kind==LessonMaterial.Kind.ATTACHMENT) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override public void afterCommit() { removeFile(id); }
            });
        }
    }
    @Transactional(readOnly=true)
    LessonMaterial download(String email,UUID id) {
        LessonMaterial item=materials.findByIdAndUserId(id,user(email).getId()).orElseThrow(this::notFound);
        if(item.kind!=LessonMaterial.Kind.ATTACHMENT)throw notFound();
        return item;
    }
    private LessonMaterial owned(String email,String chapter,String slug,UUID id,LessonMaterial.Kind kind) {
        LessonMaterial item=materials.findByIdAndUserId(id,user(email).getId()).orElseThrow(this::notFound);
        if(item.kind!=kind||!item.lesson.getId().equals(lesson(chapter,slug).getId()))throw notFound();
        return item;
    }
    private User user(String email) {
        return users.findByEmail(email).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }
    private Lesson lesson(String chapter,String slug) {
        return lessons.findByChapterSlugAndSlug(chapter,slug).orElseThrow(this::notFound);
    }
    private View view(LessonMaterial item) { return new View(item.id,item.kind.name(),item.title,item.url); }
    private ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND,"Nie ma takiego materiału.");
    }
    private String title(String title) {
        if(title==null||title.isBlank()||title.strip().length()>200)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tytuł jest wymagany i może mieć do 200 znaków.");
        return title.strip();
    }
    private String link(String value) {
        if(value!=null&&value.strip().length()<=2048) {
            try {
                URI uri=URI.create(value.strip());
                if(("https".equalsIgnoreCase(uri.getScheme())||"http".equalsIgnoreCase(uri.getScheme()))
                        &&uri.getHost()!=null&&uri.getUserInfo()==null&&uri.toASCIIString().length()<=2048) return uri.toASCIIString();
            } catch(IllegalArgumentException ignored) {}
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Podaj poprawny link http:// lub https:// (do 2048 znaków).");
    }
    private void removeFile(UUID id) {
        try { storage.delete(id); }
        catch(IOException e) { log.error("Nie mozna usunac pliku zalacznika {}",id,e); }
    }
}

