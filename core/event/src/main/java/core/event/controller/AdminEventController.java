package core.event.controller;

import core.common.event.dto.AdminEventSearchParams;
import core.common.event.dto.EventFullDto;
import core.common.event.dto.UpdEventAdminRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import core.event.service.EventService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService eventService;

    @PatchMapping("/{eventId}")
    public EventFullDto adminUpdate(@PathVariable @Positive Long eventId,
                                                    @RequestBody @Valid UpdEventAdminRequest updDto) {
        log.debug("Метод adminUpdateEvent(); eventId: {}, dto={}", eventId, updDto);
        return eventService.updateByAdmin(eventId, updDto);
    }

    @GetMapping
    public List<EventFullDto> adminSearch(@Valid @ModelAttribute AdminEventSearchParams params) {
        log.debug("Метод adminSearchEvents; {}", params);
        return eventService.searchForAdmin(params);
    }
}