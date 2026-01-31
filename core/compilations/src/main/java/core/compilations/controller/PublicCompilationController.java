package core.compilations.controller;

import core.common.compilations.dto.CompilationDto;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import core.compilations.service.CompilationService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
                                                @RequestParam(defaultValue = "10", required = false) @Positive Integer size) {
        log.debug("Метод getCompilations(); pinned={}, from={}, size={}", pinned, from, size);
        return compilationService.findAllById(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable @Positive Long compId) {
        log.debug("Метод getCompilationById(); id={}", compId);
        return compilationService.findById(compId);
    }
}