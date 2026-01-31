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
                                                        HttpServletRequest request) {
        log.debug("Метод publicSearchOne(); eventId={}", eventId);
        return eventService.getPublicBy(eventId, request);
    }

    @GetMapping
    public List<EventFullDto> publicSearchMany(@Valid @ModelAttribute UserEventSearchParams params,
                                                               HttpServletRequest request) {
        log.debug("Метод publicSearchMany(); {}", params);
        return eventService.getPublicBy(params, request);
    }

    @GetMapping("/{categoryId}/exist")
    public boolean existsByCategoryId(@PathVariable Long categoryId) {
        return eventService.existsByCategoryId(categoryId);
    }

    @GetMapping("/findAllById")
    public List<EventShortDto> findAllById(@RequestParam Set<Long> eventId) {
        return eventService.findAllById(eventId);
    }

    @GetMapping("/{eventId}/findById")
    public EventShortDto findById(@PathVariable Long eventId) {
        return eventService.findById(eventId);
    }

    @GetMapping("/{eventId}/findByIdFull")
    public EventFullDto findByIdFull(@PathVariable Long eventId) {
        return eventService.findByIdFull(eventId);
    }

    @PutMapping("/setConfirmedRequests")
    public EventFullDto setConfirmedRequests(@RequestBody EventFullDto event) {
        return eventService.setConfirmedRequests(event);
    }
}