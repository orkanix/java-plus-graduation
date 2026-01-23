package ru.practicum.ewm.compilation.mapper;

import core.common.compilations.dto.CompilationDto;
import core.common.compilations.dto.NewCompilationDto;
import core.common.compilations.dto.UpdateCompilationDto;
import core.common.event.dto.EventShortDto;
import org.mapstruct.*;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface CompilationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", ignore = true)
    Compilation toEntity(NewCompilationDto newDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", ignore = true)
    Compilation updateFromDto(UpdateCompilationDto updDto, @MappingTarget Compilation compilation);

    CompilationDto toDto(Compilation compilation);

    List<EventShortDto> mapEvents(List<Event> events);
}