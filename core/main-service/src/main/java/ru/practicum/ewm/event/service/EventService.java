package ru.practicum.ewm.event.service;

import core.common.event.dto.*;
import core.common.requests.dto.ParticipationRequestDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Set;

public interface EventService {

    // Private API:
    EventFullDto create(Long userId, NewEventDto newEventDto);

    EventFullDto getByUser(Long userId, Long eventId);

    List<EventShortDto> getAllByUser(Long userId, int from, int size);

    EventFullDto updateByUser(Long userId, Long eventId, UpdEventUserRequest updEventUserRequest);

    // Admin API:
    EventFullDto updateByAdmin(Long eventId, UpdEventAdminRequest updEventAdminRequest);

    List<EventFullDto> searchForAdmin(AdminEventSearchParams params);

    // Public API:
    EventFullDto getPublicBy(Long eventId, HttpServletRequest request);

    List<EventFullDto> getPublicBy(UserEventSearchParams params, HttpServletRequest request);

    List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId);

    UpdRequestsStatusResult updateRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest updDto);

    boolean existsByCategoryId(Long categoryId);

    List<EventShortDto> findAllById(Set<Long> eventId);
}