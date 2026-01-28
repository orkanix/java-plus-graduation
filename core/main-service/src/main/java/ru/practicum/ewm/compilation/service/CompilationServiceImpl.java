package ru.practicum.ewm.compilation.service;

import core.common.compilations.dto.CompilationDto;
import core.common.compilations.dto.NewCompilationDto;
import core.common.compilations.dto.UpdateCompilationDto;
import core.common.event.client.EventClient;
import core.common.event.dto.EventShortDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import core.common.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final EventClient eventClient;
    private final CompilationRepository compilationRepository;

    private final CompilationMapper compilationMapper;

    // Admin API:
    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto newDto) {
        log.debug("Метод create(); newDto={}", newDto);

        List<Long> events = this.findEventsBy(newDto.getEvents()).stream().map(EventShortDto::getId).toList();

        log.info(newDto.getEvents().toString());

        Compilation compilation = compilationMapper.toEntity(newDto);
        compilation.setEvents(new ArrayList<>(events));
        compilation = compilationRepository.save(compilation);

        log.info(compilation.getEvents().toString());

        return compilationMapper.toDto(compilation);
    }

    @Override
    @Transactional
    public CompilationDto update(Long compId, UpdateCompilationDto updDto) {
        log.debug("Метод update(); compId={}, updDto={}", compId, updDto);

        Compilation compilation = this.findCompilationBy(compId);
        compilation = compilationMapper.updateFromDto(updDto, compilation);

        if (updDto.getEvents() != null) {
            List<Long> events = eventClient.findAllById(updDto.getEvents()).stream().map(EventShortDto::getId).toList();
            compilation.setEvents(new ArrayList<>(events));
        }
        compilation = compilationRepository.save(compilation);

        log.debug("compilation={}", compilation);

        return compilationMapper.toDto(compilation);
    }

    @Override
    @Transactional
    public void delete(Long compId) {
        log.debug("Метод delete(); compId={}", compId);

        compilationRepository.deleteById(compId);
    }

    // Public API:
    @Override
    public List<CompilationDto> getAllBy(Boolean pinned, Integer from, Integer size) {
        log.debug("Метод getAllBy(); pinned={}, from={}, size={}", pinned, from, size);

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        Page<Compilation> compilations = pinned != null
                ? compilationRepository.findByPinned(pinned, pageable) : compilationRepository.findAll(pageable);

        return compilations.getContent()
                .stream()
                .map(compilationMapper::toDto)
                .toList();
    }

    @Override
    public CompilationDto getBy(Long compId) {
        log.debug("Метод getBy(); compId={}", compId);

        Compilation compilation = this.findCompilationBy(compId);

        log.debug("compilation={}", compilation);

        return compilationMapper.toDto(compilation);
    }


    private Compilation findCompilationBy(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(() -> new NotFoundException("Подборка не найдена"));
    }

    private List<EventShortDto> findEventsBy(Set<Long> eventsIds) {
        List<EventShortDto> events = eventClient.findAllById(eventsIds);

        if (events.size() != eventsIds.size()) {
            throw new NotFoundException("Некоторые события не найдены");
        }
        return events;
    }
}