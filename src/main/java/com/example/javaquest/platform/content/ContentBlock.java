package com.example.javaquest.platform.content;

import com.example.javaquest.platform.chapter.Lesson;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/** Jeden blok teorii lekcji (patrz {@link ContentBlockType}). */
@Entity
@Table(name = "content_blocks")
public class ContentBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContentBlockType type;

    @Column(nullable = false)
    private String heading;

    // columnDefinition = "TEXT", NIE @Lob: Hibernate + PostgreSQL mapuje "@Lob String" na
    // kolumne typu "oid" (Large Object), a zapis przez Postgresowe LO API (fastpath) wisial
    // W NIESKONCZONOSC na pierwszym INSERCIE (potwierdzone jstack + pg_stat_activity -
    // "idle in transaction" i watek zablokowany w LargeObjectManager.open) - zwykly TEXT
    // (bez limitu dlugosci w Postgresie, wiec bez utraty mozliwosci wobec @Lob) uzywa
    // normalnego setString, bez LO API.
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    /** Tylko dla {@link ContentBlockType#CODE_EXAMPLE} - moze byc null dla innych typow. */
    @Column(columnDefinition = "TEXT")
    private String code;

    protected ContentBlock() {
        // wymagane przez JPA
    }

    public ContentBlock(Lesson lesson, int sortOrder, ContentBlockType type, String heading, String body,
                         String code) {
        this.lesson = lesson;
        this.sortOrder = sortOrder;
        this.type = type;
        this.heading = heading;
        this.body = body;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public ContentBlockType getType() {
        return type;
    }

    public String getHeading() {
        return heading;
    }

    public String getBody() {
        return body;
    }

    public String getCode() {
        return code;
    }
}
