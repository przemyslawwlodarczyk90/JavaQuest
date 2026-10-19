package com.example.javaquest.platform.attachments;

import com.example.javaquest.platform.chapter.Lesson;
import com.example.javaquest.platform.user.User;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "lesson_materials", indexes = @Index(name = "lesson_material_owner", columnList = "user_id,lesson_id"))
class LessonMaterial {
    enum Kind { LINK, ATTACHMENT }
    @Id UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "user_id", nullable = false)
    User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "lesson_id", nullable = false)
    Lesson lesson;
    @Enumerated(EnumType.STRING) @Column(nullable = false) Kind kind;
    @Column(nullable = false, length = 200) String title;
    @Column(nullable = false, length = 2048) String url;
    protected LessonMaterial() {}
    LessonMaterial(UUID id, User user, Lesson lesson, Kind kind, String title, String url) {
        this.id=id; this.user=user; this.lesson=lesson; this.kind=kind; this.title=title; this.url=url;
    }
}

