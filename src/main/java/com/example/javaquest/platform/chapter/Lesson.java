package com.example.javaquest.platform.chapter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Lekcja platformy edukacyjnej JavaQuest (np. "00_JavaPlatformBasics" w rozdziale
 * "_01_fundamentals"). W Fazie 1 to tylko metadane nawigacyjne (slug/tytul/kolejnosc) -
 * wlasciwa tresc (teoria/zadania/quiz) dochodzi w Fazie 2+ jako osobne encje
 * powiazane z lekcja (patrz EDU_PLATFORM_PLAN.md, sekcja 4.1).
 */
@Entity
@Table(name = "lessons", uniqueConstraints = @UniqueConstraint(columnNames = {"chapter_id", "slug"}))
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String title;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    protected Lesson() {
        // wymagane przez JPA
    }

    public Lesson(Chapter chapter, String slug, String title, int sortOrder) {
        this.chapter = chapter;
        this.slug = slug;
        this.title = title;
        this.sortOrder = sortOrder;
    }

    public Long getId() {
        return id;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public int getSortOrder() {
        return sortOrder;
    }
}
