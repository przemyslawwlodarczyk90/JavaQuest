package com.example.javaquest.platform.chapter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Rozdzial platformy edukacyjnej JavaQuest (np. "_01_fundamentals" / "Podstawy Javy").
 * Odpowiada 1:1 jednemu wpisowi z {@code _00_tableOfContents/_TableOfContents.ROZDZIALY}
 * (patrz {@link ChapterSeedData} - celowo osobna, wlasna kopia tej listy, zeby nie
 * modyfikowac/importowac paczkowo-prywatnych skladowych pliku bedacego "podstawa
 * programowa" kursu - patrz EDU_PLATFORM_PLAN.md).
 */
@Entity
@Table(name = "chapters")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String title;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    protected Chapter() {
        // wymagane przez JPA
    }

    public Chapter(String slug, String title, int sortOrder) {
        this.slug = slug;
        this.title = title;
        this.sortOrder = sortOrder;
    }

    public Long getId() {
        return id;
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
