package core.common.compilations.dto;

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
public class UpdateCompilationDto {

    @Builder.Default
    private Set<Long> events = new HashSet<>();

    private Boolean pinned;

    @Size(max = 50)
    private String title;
}