package ru.practicum.ewm.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentFullDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.dto.UpdCommentDto;
import ru.practicum.ewm.comment.service.CommentService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events/{eventId}/comments")
public class PrivateCommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentFullDto addComment(@RequestBody @Valid NewCommentDto dto,
                                                     @PathVariable Long eventId,
                                                     @PathVariable Long userId) {
        log.info("Метод addComment(); even");
        return commentService.add(dto, eventId, userId);
    }

    @GetMapping
    public List<CommentFullDto> getAllCommentsBy(@PathVariable Long userId,
                                                                 @PathVariable Long eventId) {
        log.info("Метод getCommentsByUserId(); userId={} eventId={}", userId, eventId);
        return commentService.getAllBy(userId, eventId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long userId,
                              @PathVariable Long eventId,
                              @PathVariable Long commentId) {
        log.info("Метод deleteComment(); userId={}, eventId={}, commentId={}", userId, eventId, commentId);
        commentService.delete(userId, commentId);
    }

    @PatchMapping("/{commentId}")
    public CommentFullDto updateComment(@PathVariable Long userId,
                                                        @PathVariable Long eventId,
                                                        @PathVariable Long commentId,
                                                        @Valid @RequestBody UpdCommentDto updDto) {
        log.info("Метод updateComment(); updCommentDto={}", updDto);
        return commentService.update(userId, commentId, updDto);
    }
}