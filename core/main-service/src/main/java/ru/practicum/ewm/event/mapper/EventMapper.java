package ru.practicum.ewm.event.mapper;

import core.common.category.dto.CategoryDto;
import core.common.event.dto.*;
import core.common.user.dto.UserShortDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.model.Event;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class EventMapper {

    public Event toEntity(NewEventDto newEventDto, Long userId) {
        if (newEventDto == null) return null;

        Event event = new Event();

        event.setTitle(newEventDto.getTitle());
        event.setAnnotation(newEventDto.getAnnotation());
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(toInstantForMap(newEventDto.getEventDate()));
        event.setPaid(newEventDto.getPaid() != null ? newEventDto.getPaid() : false);
        event.setParticipantLimit(newEventDto.getParticipantLimit() != null ? newEventDto.getParticipantLimit() : 0);
        event.setRequestModeration(newEventDto.getRequestModeration() != null ? newEventDto.getRequestModeration() : true);

        event.setCategory(newEventDto.getCategory());
        event.setInitiator(userId);

        return event;
    }

    public EventShortDto toShortDto(Event event, CategoryDto categoryDto, UserShortDto userShortDto) {
        if (event == null) return null;

        EventShortDto dto = new EventShortDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setAnnotation(event.getAnnotation());
        dto.setCategory(categoryDto);
        dto.setEventDate(toLocalDateTimeForMap(event.getEventDate()));
        dto.setPaid(event.getPaid());
        dto.setConfirmedRequests(event.getConfirmedRequests());
        dto.setViews(event.getViews());
        dto.setInitiator(userShortDto);

        return dto;
    }

    public EventFullDto toFullDto(Event event, CategoryDto categoryDto, UserShortDto userShortDto) {
        if (event == null) return null;

        EventFullDto dto = new EventFullDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setAnnotation(event.getAnnotation());
        dto.setCategory(categoryDto);
        dto.setEventDate(toLocalDateTimeForMap(event.getEventDate()));
        dto.setCreatedOn(toLocalDateTimeForMap(event.getCreatedOn()));
        dto.setPublishedOn(toLocalDateTimeForMap(event.getPublishedOn()));
        dto.setPaid(event.getPaid());
        dto.setParticipantLimit(event.getParticipantLimit());
        dto.setRequestModeration(event.getRequestModeration());
        dto.setConfirmedRequests(event.getConfirmedRequests());
        dto.setState(event.getState());
        dto.setViews(event.getViews());
        dto.setInitiator(userShortDto);
        dto.setLocation(event.getLocation());

        return dto;
    }

    public void updateFromDto(UpdEventUserRequest updEventUserRequest, Event event) {
        if (updEventUserRequest == null || event == null) return;

        if (updEventUserRequest.getEventDate() != null)
            event.setEventDate(toInstantForUpdate(updEventUserRequest.getEventDate(), event.getEventDate()));

        if (updEventUserRequest.getPaid() != null)
            event.setPaid(updEventUserRequest.getPaid());

        if (updEventUserRequest.getParticipantLimit() != null)
            event.setParticipantLimit(updEventUserRequest.getParticipantLimit());

        if (updEventUserRequest.getRequestModeration() != null)
            event.setRequestModeration(updEventUserRequest.getRequestModeration());

        if (updEventUserRequest.getDescription() != null)
            event.setDescription(updEventUserRequest.getDescription());

        if (updEventUserRequest.getTitle() != null)
            event.setTitle(updEventUserRequest.getTitle());

        if (updEventUserRequest.getAnnotation() != null)
            event.setAnnotation(updEventUserRequest.getAnnotation());
    }

    public void updateFromDto(UpdEventAdminRequest updEventAdminRequest, Event event) {
        if (updEventAdminRequest == null || event == null) return;

        if (updEventAdminRequest.getEventDate() != null)
            event.setEventDate(toInstantForUpdate(updEventAdminRequest.getEventDate(), event.getEventDate()));

        if (updEventAdminRequest.getPaid() != null)
            event.setPaid(updEventAdminRequest.getPaid());

        if (updEventAdminRequest.getParticipantLimit() != null)
            event.setParticipantLimit(updEventAdminRequest.getParticipantLimit());

        if (updEventAdminRequest.getRequestModeration() != null)
            event.setRequestModeration(updEventAdminRequest.getRequestModeration());

        if (updEventAdminRequest.getDescription() != null)
            event.setDescription(updEventAdminRequest.getDescription());

        if (updEventAdminRequest.getTitle() != null)
            event.setTitle(updEventAdminRequest.getTitle());

        if (updEventAdminRequest.getAnnotation() != null)
            event.setAnnotation(updEventAdminRequest.getAnnotation());
    }

    public Instant toInstantForMap(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toInstant(ZoneOffset.UTC) : null;
    }

    public LocalDateTime toLocalDateTimeForMap(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
    }

    public Instant toInstantForUpdate(LocalDateTime newDateTime, Instant currentValue) {
        return newDateTime != null ? newDateTime.toInstant(ZoneOffset.UTC) : currentValue;
    }
}