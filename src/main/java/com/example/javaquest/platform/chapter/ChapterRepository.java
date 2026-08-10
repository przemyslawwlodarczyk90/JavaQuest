package com.example.javaquest.platform.chapter;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    Optional<Chapter> findBySlug(String slug);

    List<Chapter> findAllByOrderBySortOrderAsc();
}
