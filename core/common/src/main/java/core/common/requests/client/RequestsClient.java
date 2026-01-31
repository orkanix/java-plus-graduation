package core.common.requests.client;

import core.common.requests.dto.ParticipationRequestDto;
import core.common.requests.dto.RequestStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@FeignClient(name = "requests-service")
public interface RequestsClient {

    String PRIVATE_PREFIX = "/users/{userId}/requests";
    String PUBLIC_PREFIX = "/requests";

    @PostMapping(PRIVATE_PREFIX)
    ParticipationRequestDto createRequest(@PathVariable Long userId,
                                          @RequestParam Long eventId);

    @GetMapping(PRIVATE_PREFIX)
    List<ParticipationRequestDto> getRequests(@PathVariable Long userId);

    @PatchMapping(PRIVATE_PREFIX + "{requestId}/cancel")
    ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                          @PathVariable Long requestId);

    @GetMapping(PUBLIC_PREFIX + "/{eventId}/findAllByEvent")
    List<ParticipationRequestDto> findAllByEvent(@PathVariable Long eventId);

    @PostMapping(PUBLIC_PREFIX + "/findAllByIdIn")
    List<ParticipationRequestDto> findAllByIdIn(@RequestBody Set<Long> requestIds);

    @PostMapping(PUBLIC_PREFIX + "/updateStatuses/{status}")
    List<ParticipationRequestDto> updateStatuses(@RequestBody List<ParticipationRequestDto> requests, @PathVariable RequestStatus status);
}
