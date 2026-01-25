package ru.practicum.ewm.request.mapper;

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

    private LocalDateTime toLocalDateTime(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
    }
}