package ru.practicum.ewm.compilation.controller;

import core.common.compilations.dto.CompilationDto;
import core.common.compilations.dto.NewCompilationDto;
import core.common.compilations.dto.UpdateCompilationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.service.CompilationService;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto newDto) {
        log.debug("Метод createCompilation(); dto={}", newDto);
        return compilationService.create(newDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        log.debug("Метод deleteCompilation(); compId={}", compId);
        compilationService.delete(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable Long compId,
                                                            @Valid @RequestBody UpdateCompilationDto updDto) {
        log.debug("Метод updateCompilation(); compId={}, updDto={}", compId, updDto);
        return compilationService.update(compId, updDto);
    }
}