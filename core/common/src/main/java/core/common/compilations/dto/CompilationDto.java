package core.common.compilations.dto;

import core.common.event.dto.EventShortDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

    @NotNull
    private Long id;

    @Builder.Default
    private Set<EventShortDto> events = new HashSet<>();

    @NotNull
    private Boolean pinned;

    @NotBlank
    private String title;
}