package core.common.compilations.client;

import core.common.compilations.dto.CompilationDto;
import core.common.compilations.dto.NewCompilationDto;
import core.common.compilations.dto.UpdateCompilationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "compilation-service")
public interface CompilationClient {

    String ADMIN_PREFIX = "/admin/compilations";
    String PUBLIC_PREFIX = "/categories";

    @PostMapping(ADMIN_PREFIX)
    CompilationDto createCompilation(@RequestBody NewCompilationDto newDto);

    @DeleteMapping(ADMIN_PREFIX + "/{compId}")
    void deleteCompilation(@PathVariable Long compId);

    @PatchMapping(ADMIN_PREFIX + "/{compId}")
    CompilationDto updateCompilation(@PathVariable Long compId,
                                     @RequestBody UpdateCompilationDto updDto);

    @GetMapping(PUBLIC_PREFIX)
    List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                         @RequestParam(defaultValue = "0", required = false) Integer from,
                                         @RequestParam(defaultValue = "10", required = false) Integer size);

    @GetMapping(PUBLIC_PREFIX + "/{compId}")
    CompilationDto getCompilationById(@PathVariable Long compId);
}
