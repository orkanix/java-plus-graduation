package core.event.controller;

import core.common.event.dto.EventFullDto;
import core.common.event.dto.EventShortDto;
import core.common.event.dto.UserEventSearchParams;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import core.event.service.EventService;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {

    private final EventService eventService;

    @GetMapping("/{eventId}")
    public EventFullDto publicSearchOne(@PathVariable @Positive Long eventId,
                                        @RequestHeader("X-EWM-USER-ID") @Positive Long userId,
                                        HttpServletRequest request) {
        log.debug("Метод publicSearchOne(); userId={}, eventId={}", userId, eventId);
        return eventService.findPublicBy(userId, eventId, request);
    }

    @GetMapping
    public List<EventFullDto> publicSearchMany(@ModelAttribute @Valid UserEventSearchParams params,
                                               HttpServletRequest request) {
        log.debug("Метод publicSearchMany(); {}", params);
        return eventService.findPublicBy(params, request);
    }

    @GetMapping("/{categoryId}/exist")
    public boolean existsByCategoryId(@PathVariable @Positive Long categoryId) {
        return eventService.existsByCategoryId(categoryId);
    }

    @GetMapping("/findAllById")
    public List<EventShortDto> findAllById(@RequestParam Set<@Positive Long> eventId) {
        return eventService.findAllById(eventId);
    }

    @GetMapping("/{eventId}/findById")
    public EventShortDto findById(@PathVariable @Positive Long eventId) {
        return eventService.findById(eventId);
    }

    @GetMapping("/{eventId}/findByIdFull")
    public EventFullDto findByIdFull(@PathVariable @Positive Long eventId) {
        return eventService.findByIdFull(eventId);
    }

    @GetMapping("/recommendations")
    public List<EventShortDto> findRecommendations(@RequestHeader("X-EWM-USER-ID") @Positive Long userId) {
        return eventService.findRecommendations(userId);
    }

    @PutMapping("/setConfirmedRequests")
    public EventFullDto setConfirmedRequests(@RequestBody @Valid EventFullDto event) {
        return eventService.setConfirmedRequests(event);
    }

    @PutMapping("{eventId}/like")
    public void likeEvent(@RequestHeader("X-EWM-USER-ID") @Positive Long userId,
                          @PathVariable @Positive Long eventId) {
        eventService.likeEvent(userId, eventId);
    }
}