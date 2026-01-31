package core.requests.controller;

import core.common.requests.dto.ParticipationRequestDto;
import core.common.requests.dto.RequestStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import core.requests.service.RequestService;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class PublicRequestController {

    private final RequestService requestService;

    @GetMapping("/{eventId}/findAllByEvent")
    public List<ParticipationRequestDto> findAllByEvent(@PathVariable Long eventId) {
        return requestService.findAllByEvent(eventId);
    }

    @PostMapping("/findAllByIdIn")
    public List<ParticipationRequestDto> findAllByIdIn(@RequestBody Set<Long> requestIds) {
        return requestService.findAllByIdIn(requestIds);
    }

    @PostMapping("/updateStatuses/{status}")
    public List<ParticipationRequestDto> updateStatuses(@RequestBody List<ParticipationRequestDto> requests, @PathVariable RequestStatus status) {
        return requestService.updateStatuses(requests, status);
    }
}
