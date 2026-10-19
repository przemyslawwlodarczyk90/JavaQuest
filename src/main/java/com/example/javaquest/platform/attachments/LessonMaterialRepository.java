package com.example.javaquest.platform.attachments;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
interface LessonMaterialRepository extends JpaRepository<LessonMaterial, UUID> {
    List<LessonMaterial> findByUserIdAndLessonIdOrderByTitleAscIdAsc(Long userId, Long lessonId);
    Optional<LessonMaterial> findByIdAndUserId(UUID id, Long userId);
}

