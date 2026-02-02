package ru.practicum.ewm.comment.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.comment.dto.CommentFullDto;
import ru.practicum.ewm.comment.dto.CommentPublicDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.dto.UpdCommentDto;
import ru.practicum.ewm.comment.model.Comment;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class CommentMapper {
    public Comment toEntity(NewCommentDto dto) {
        if (dto == null) return null;

        return Comment.builder()
                .annotation(dto.getAnnotation())
                .text(dto.getText())
                .build();
    }

    public CommentFullDto toFullDto(Comment comment) {
        if (comment == null) return null;

        return CommentFullDto.builder()
                .id(comment.getId())
                .authorId(comment.getAuthor())
                .eventId(comment.getEvent())
                .annotation(comment.getAnnotation())
                .text(comment.getText())
                .publishedOn(toLocalDateTime(comment.getPublishedOn()))
                .state(comment.getState())
                .build();
    }

    public CommentPublicDto toPublicDto(Comment comment) {
        if (comment == null) return null;

        return CommentPublicDto.builder()
                .authorName(comment.getAuthor().toString())
                .eventTitle(comment.getEvent().toString())
                .annotation(comment.getAnnotation())
                .text(comment.getText())
                .publishedOn(toLocalDateTime(comment.getPublishedOn()))
                .build();
    }

    public void updateFromDto(UpdCommentDto dto, Comment comment) {
        if (dto == null || comment == null) return;

        if (dto.getAnnotation() != null) {
            comment.setAnnotation(dto.getAnnotation());
        }
        if (dto.getText() != null) {
            comment.setText(dto.getText());
        }
    }

    public LocalDateTime toLocalDateTime(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
    }
}
