package core.requests.service;

import core.common.requests.dto.ParticipationRequestDto;
import core.common.requests.dto.RequestStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

public interface RequestService {

    ParticipationRequestDto create(Long userId, Long eventId);

    List<ParticipationRequestDto> getAllBy(Long userId);

    ParticipationRequestDto cancel(Long userId, Long requestId);

    List<ParticipationRequestDto> findAllByEvent(Long eventId);

    List<ParticipationRequestDto> findAllByIdIn(Set<Long> requestIds);

    List<ParticipationRequestDto> updateStatuses(List<ParticipationRequestDto> requests, RequestStatus status);
}