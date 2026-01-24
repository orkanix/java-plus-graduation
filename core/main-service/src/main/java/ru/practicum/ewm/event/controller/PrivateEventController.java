package ru.practicum.ewm.event.controller;

import core.common.event.dto.*;
import core.common.requests.dto.ParticipationRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.service.EventService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable("userId") @NotNull @Positive Long userId,
                                               @RequestBody @Valid final NewEventDto newDto) {
        log.debug("Метод create(); userId = {}; newDto = {}", userId, newDto);
        return eventService.create(userId, newDto);
    }

    @GetMapping
    public List<EventShortDto> findAll(@PathVariable("userId") @Positive Long userId,
                                                       @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                       @RequestParam(defaultValue = "10") @Positive int size) {
        log.debug("Метод findAll(); userId={}, from={}, size={}", userId, from, size);
        return eventService.getAllByUser(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto find(@PathVariable("userId") @Positive Long userId,
                                             @PathVariable("eventId") @Positive Long eventId) {
        log.debug("Метод find(); userId={}, eventId={}", userId, eventId);
        return eventService.getByUser(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable("userId") @Positive Long userId,
                                               @PathVariable("eventId") @Positive Long eventId,
                                               @RequestBody @Valid final UpdEventUserRequest updDto) {
        log.debug("Метод update(); userId={}, eventId={}, updDto={}", userId, eventId, updDto);
        return eventService.updateByUser(userId, eventId, updDto);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getUserRequests(@PathVariable @Positive Long userId,
                                                                         @PathVariable @Positive Long eventId) {
        log.debug("Метод getUserRequests(); userId={}, eventId={}", userId, eventId);
        return eventService.getEventRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public UpdRequestsStatusResult updateRequests(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId,
            @RequestBody @Valid EventRequestStatusUpdateRequest updDto
    ) {
        log.debug("Метод updateRequest(); userId={}, eventId={}", userId, eventId);
        return eventService.updateRequests(userId, eventId, updDto);
    }
}