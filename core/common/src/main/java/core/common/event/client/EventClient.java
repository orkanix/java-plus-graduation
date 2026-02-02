package core.common.event.client;

import core.common.event.dto.*;
import core.common.requests.dto.ParticipationRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@FeignClient(name = "event-service")
public interface EventClient {

    String ADMIN_PREFIX = "/admin/events";
    String PRIVATE_PREFIX = "/users/{userId}/events";
    String PUBLIC_PREFIX = "/events";

    @PatchMapping(ADMIN_PREFIX + "/{eventId}")
    EventFullDto adminUpdate(@PathVariable Long eventId,
                             @RequestBody UpdEventAdminRequest updDto);

    @GetMapping(ADMIN_PREFIX)
    List<EventFullDto> adminSearch(@ModelAttribute AdminEventSearchParams params);

    @PostMapping(PRIVATE_PREFIX)
    EventFullDto create(@PathVariable("userId") Long userId,
                        @RequestBody final NewEventDto newDto);

    @GetMapping(PRIVATE_PREFIX)
    List<EventShortDto> findAll(@PathVariable("userId") Long userId,
                                @RequestParam(defaultValue = "0") int from,
                                @RequestParam(defaultValue = "10") int size);

    @GetMapping(PRIVATE_PREFIX + "/{eventId}")
    EventFullDto find(@PathVariable("userId") Long userId,
                      @PathVariable("eventId") Long eventId);

    @PatchMapping(PRIVATE_PREFIX + "/{eventId}")
    EventFullDto update(@PathVariable("userId") Long userId,
                        @PathVariable("eventId") Long eventId,
                        @RequestBody final UpdEventUserRequest updDto);

    @GetMapping(PRIVATE_PREFIX + "/{eventId}/requests")
    List<ParticipationRequestDto> getUserEvents(@PathVariable Long userId,
                                                @PathVariable Long eventId);

    @PatchMapping(PRIVATE_PREFIX + "/{eventId}/requests")
    UpdRequestsStatusResult updateRequests(@PathVariable Long userId,
                                           @PathVariable Long eventId,
                                           @RequestBody EventRequestStatusUpdateRequest updDto);

    @GetMapping(PRIVATE_PREFIX + "/{eventId}/existsByIdAndInitiator")
    boolean existsByIdAndInitiator(@PathVariable Long userId, @PathVariable Long eventId);

    @GetMapping(PUBLIC_PREFIX + "/{categoryId}/exist")
    boolean existsByCategoryId(@PathVariable Long categoryId);

    @GetMapping(PUBLIC_PREFIX + "/findAllById")
    List<EventShortDto> findAllById(@RequestParam Set<Long> eventId);

    @GetMapping(PUBLIC_PREFIX + "/{eventId}/findById")
    EventShortDto findById(@PathVariable Long eventId);

    @GetMapping(PUBLIC_PREFIX + "/{eventId}/findByIdFull")
    EventFullDto findByIdFull(@PathVariable Long eventId);

    @PutMapping(PUBLIC_PREFIX + "/setConfirmedRequests")
    EventFullDto setConfirmedRequests(@RequestBody EventFullDto event);
}
