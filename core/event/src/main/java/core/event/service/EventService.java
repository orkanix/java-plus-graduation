package core.event.service;

import core.common.event.dto.*;
import core.common.requests.dto.ParticipationRequestDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Set;

public interface EventService {

    // Private API:
    EventFullDto create(Long userId, NewEventDto newEventDto);

    EventFullDto findByUser(Long userId, Long eventId);

    List<EventShortDto> findAllByUser(Long userId, int from, int size);

    EventFullDto updateByUser(Long userId, Long eventId, UpdEventUserRequest updEventUserRequest);

    // Admin API:
    EventFullDto updateByAdmin(Long eventId, UpdEventAdminRequest updEventAdminRequest);

    List<EventFullDto> searchForAdmin(AdminEventSearchParams params);

    // Public API:
    EventFullDto findPublicBy(Long eventId, HttpServletRequest request);

    List<EventFullDto> findPublicBy(UserEventSearchParams params, HttpServletRequest request);

    List<ParticipationRequestDto> findEventRequests(Long userId, Long eventId);

    UpdRequestsStatusResult updateRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest updDto);

    boolean existsByCategoryId(Long categoryId);

    List<EventShortDto> findAllById(Set<Long> eventId);

    boolean existsByIdAndInitiator(Long userId, Long eventId);

    EventShortDto findById(Long eventId);

    EventFullDto findByIdFull(Long eventId);

    EventFullDto setConfirmedRequests(EventFullDto event);
}