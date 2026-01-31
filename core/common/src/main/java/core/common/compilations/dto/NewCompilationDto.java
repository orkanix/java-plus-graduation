package core.common.compilations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCompilationDto {

    @Builder.Default
    private Set<Long> events = new HashSet<>();

    @NotNull
    @Builder.Default
    private Boolean pinned = false;

    @NotBlank
    @Size(max = 50)
    private String title;
}
