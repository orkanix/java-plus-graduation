package core.requests.service;

import core.common.event.client.EventClient;
import core.common.event.dto.EventFullDto;
import core.common.event.dto.EventState;
import core.common.requests.dto.ParticipationRequestDto;
import core.common.requests.dto.RequestStatus;
import core.common.user.client.UserClient;
import core.common.user.dto.UserShortDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import core.common.exception.ConflictException;
import core.common.exception.NotFoundException;
import core.requests.mapper.RequestMapper;
import core.requests.model.Request;
import core.requests.repository.RequestRepository;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final UserClient userClient;
    private final EventClient eventClient;
    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public ParticipationRequestDto create(Long userId, Long eventId) {
        log.debug("Метод createRequest(); userId: {}, eventId: {}", userId, eventId);

        UserShortDto user = userClient.findUserById(userId);
        EventFullDto event = eventClient.findByIdFull(eventId);

        if (eventClient.findById(event.getId()).getInitiator().getId().equals(user.getId())) {
            throw new ConflictException("Нельзя участвовать в собственном событии");
        }

        if (requestRepository.existsByEventAndRequester(event.getId(), user.getId())) {
            throw new ConflictException("Request уже создан ранее");
        }

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии");
        }

        long limit = event.getParticipantLimit();
        long confirm = event.getConfirmedRequests();

        if (limit > 0 && confirm >= limit) {
            throw new ConflictException("Достигнут лимит запросов на участие в событии");
        }

        RequestStatus status =
                (!event.getRequestModeration() || limit == 0) ? RequestStatus.CONFIRMED : RequestStatus.PENDING;

        if (status == RequestStatus.CONFIRMED) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);

            eventClient.setConfirmedRequests(event);
        }

        Request request = Request.builder()
                .requester(user.getId())
                .event(event.getId())
                .status(status)
                .build();
        request = requestRepository.save(request);

        return requestMapper.toDto(request);
    }

    @Override
    public List<ParticipationRequestDto> findAllById(Long userId) {
        log.debug("Метод findAllById(); userId: {}", userId);

        List<Request> result = requestRepository.findAllByRequester(userId);

        return result.stream()
                .map(requestMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        log.debug("Метод cancel(); userId: {}, requestId: {}", userId, requestId);

        userClient.findUserById(userId);
        Request request = this.findRequestById(requestId);
        request.setStatus(RequestStatus.CANCELED);

        if (!request.getRequester().equals(userId)) {
            throw new ConflictException("User id={} не является автором этого запроса", userId);
        }
        request = requestRepository.save(request);

        return requestMapper.toDto(request);
    }

    @Override
    public List<ParticipationRequestDto> findAllByEvent(Long eventId) {
        log.debug("Метод findAllByEvent(); eventId: {}", eventId);

        return requestRepository.findAllByEvent(eventId).stream().map(requestMapper::toDto).toList();
    }

    @Override
    public List<ParticipationRequestDto> findAllByIdIn(Set<Long> requestIds) {
        log.debug("Метод findAllByIdIn(); requestIds: {}", requestIds);

        return requestRepository.findAllByIdIn(requestIds).stream().map(requestMapper::toDto).toList();
    }

    @Override
    public List<ParticipationRequestDto> updateStatuses(List<ParticipationRequestDto> requests, RequestStatus status) {
        log.debug("Метод updateStatuses(); requests: {}, status: {}", requests, status);

        List<ParticipationRequestDto> updatedRequests = requests.stream()
                .peek(request -> request.setStatus(status))
                .toList();

        return requestRepository
                .saveAllAndFlush(
                        updatedRequests.stream()
                                .map(requestMapper::toEntity)
                                .toList()
                )
                .stream()
                .map(requestMapper::toDto)
                .toList();
    }

    private Request findRequestById(Long requestId) {
        log.debug("Метод findRequestById(); requestId: {}", requestId);

        return requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request id={} не найден", requestId));
    }
}