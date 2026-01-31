package core.compilations.service;

import core.common.compilations.dto.CompilationDto;
import core.common.compilations.dto.NewCompilationDto;
import core.common.compilations.dto.UpdateCompilationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompilationService {

    // Admin API:
    CompilationDto create(NewCompilationDto newCompilationDto);

    CompilationDto update(Long compId, UpdateCompilationDto updateCompilationDto);

    void delete(Long compId);

    // Public API:
    CompilationDto getBy(Long compId);

    List<CompilationDto> getAllBy(Boolean pinned, Integer from, Integer size);
}