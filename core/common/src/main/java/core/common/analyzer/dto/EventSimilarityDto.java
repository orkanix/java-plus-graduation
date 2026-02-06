package core.common.analyzer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventSimilarityDto {

    private Long id;

    @NotNull
    @Positive
    private Long eventA;

    @NotNull
    @Positive
    private Long eventB;

    @NotNull
    private double score;

    @NotNull
    private LocalDateTime timestamp;
}
