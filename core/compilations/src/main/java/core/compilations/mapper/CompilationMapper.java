package core.compilations.mapper;

import core.common.compilations.dto.CompilationDto;
import core.common.compilations.dto.NewCompilationDto;
import core.common.compilations.dto.UpdateCompilationDto;
import core.common.event.dto.EventShortDto;
import org.springframework.stereotype.Component;
import core.compilations.model.Compilation;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompilationMapper {

    public Compilation toEntity(NewCompilationDto newDto) {
        if (newDto == null) return null;

        Compilation compilation = new Compilation();
        compilation.setTitle(newDto.getTitle());
        compilation.setPinned(newDto.getPinned());
        compilation.setEvents(new ArrayList<>());

        return compilation;
    }

    public Compilation updateFromDto(UpdateCompilationDto updDto, Compilation compilation) {
        if (updDto == null || compilation == null) return null;

        if (updDto.getTitle() != null) {
            compilation.setTitle(updDto.getTitle());
        }

        if (updDto.getPinned() != null) {
            compilation.setPinned(updDto.getPinned());
        }

        if (updDto.getEvents() != null) {
            compilation.setEvents(new ArrayList<>(updDto.getEvents()));
        }

        return compilation;
    }

    public CompilationDto toDto(Compilation compilation) {
        if (compilation == null) return null;

        CompilationDto dto = new CompilationDto();
        dto.setId(compilation.getId());
        dto.setTitle(compilation.getTitle());
        dto.setPinned(compilation.getPinned());

        List<EventShortDto> events = new ArrayList<>();
        if (compilation.getEvents() != null) {
            for (Long id : compilation.getEvents()) {
                EventShortDto e = new EventShortDto();
                e.setId(id);
                events.add(e);
            }
        }
        dto.setEvents(events);

        return dto;
    }
}