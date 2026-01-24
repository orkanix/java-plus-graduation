package core.common.requests.client;

import core.common.requests.dto.ParticipationRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "main-service")
public interface RequestsClient {

    String PRIVATE_PREFIX = "/users/{userId}/requests";

    @PostMapping(PRIVATE_PREFIX)
    ParticipationRequestDto createRequest(@PathVariable Long userId,
                                          @RequestParam Long eventId);

    @GetMapping(PRIVATE_PREFIX)
    List<ParticipationRequestDto> getRequests(@PathVariable Long userId);

    @PatchMapping(PRIVATE_PREFIX + "{requestId}/cancel")
    ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                          @PathVariable Long requestId);
}
