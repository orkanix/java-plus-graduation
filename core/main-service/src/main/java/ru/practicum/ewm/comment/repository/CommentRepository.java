package ru.practicum.ewm.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.comment.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByEventAndAuthor(Long eventId, Long userId);

    List<Comment> findByEvent(Long eventId);

    boolean existsByIdAndEvent(Long id, Long eventId);

    boolean existsByIdAndAuthor(Long commentId, Long authorId);
}