package core.common.event.client;

import core.common.event.dto.*;
import core.common.requests.dto.ParticipationRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "main-service")
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
    List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId,
                                                  @PathVariable Long eventId);

    @PatchMapping(PRIVATE_PREFIX + "/{eventId}/requests")
    UpdRequestsStatusResult updateRequests(@PathVariable Long userId,
                                           @PathVariable Long eventId,
                                           @RequestBody EventRequestStatusUpdateRequest updDto);

    @GetMapping(PUBLIC_PREFIX + "/{eventId}")
    EventFullDto publicSearchOne(@PathVariable Long eventId,
                                 HttpServletRequest request);

    @GetMapping(PUBLIC_PREFIX)
    List<EventFullDto> publicSearchMany(@ModelAttribute UserEventSearchParams params,
                                        HttpServletRequest request);
}
