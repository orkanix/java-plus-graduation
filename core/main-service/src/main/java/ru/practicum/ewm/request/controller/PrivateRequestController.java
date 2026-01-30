package ru.practicum.ewm.request.controller;

import core.common.requests.dto.ParticipationRequestDto;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestController {

    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(@PathVariable @Positive Long userId,
                                                                 @RequestParam @Positive Long eventId) {
        log.debug("Метод createRequest(); userId={}, eventId={}", userId, eventId);
        return requestService.create(userId, eventId);
    }

    @GetMapping
    public List<ParticipationRequestDto> getRequests(@PathVariable @Positive Long userId) {
        log.debug("Метод getRequests(); userId={}", userId);
        return requestService.getAllBy(userId);
    }

    @PatchMapping("{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable @Positive Long userId,
                                                                 @PathVariable @Positive Long requestId) {
        log.debug("Метод cancelRequest(); userId={}, requestId={}", userId, requestId);
        return requestService.cancel(userId, requestId);
    }
}