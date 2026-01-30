package ru.practicum.ewm.request.mapper;

import core.common.event.dto.EventFullDto;
import core.common.event.dto.UpdEventUserRequest;
import core.common.requests.dto.ParticipationRequestDto;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.request.model.Request;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class RequestMapper {

    public ParticipationRequestDto toDto(Request request) {
        if (request == null) return null;

        ParticipationRequestDto dto = new ParticipationRequestDto();

        dto.setId(request.getId());

        if (request.getEvent() != null) {
            dto.setEvent(request.getEvent());
        }

        if (request.getRequester() != null) {
            dto.setRequester(request.getRequester());
        }

        dto.setStatus(request.getStatus());
        dto.setCreated(toLocalDateTime(request.getCreated()));

        return dto;
    }

    public Request toEntity(ParticipationRequestDto dto) {
        if (dto == null) return null;

        Request request = new Request();
        request.setId(dto.getId());
        request.setEvent(dto.getEvent());
        request.setRequester(dto.getRequester());
        request.setStatus(dto.getStatus());
        request.setCreated(dto.getCreated() != null ? dto.getCreated().toInstant(ZoneOffset.UTC) : null);

        return request;
    }

    private LocalDateTime toLocalDateTime(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
    }
}