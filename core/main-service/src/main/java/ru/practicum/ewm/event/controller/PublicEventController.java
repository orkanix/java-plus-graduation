package ru.practicum.ewm.event.controller;

import core.common.event.dto.EventFullDto;
import core.common.event.dto.UserEventSearchParams;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.service.EventService;

import java.util.List;

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
}