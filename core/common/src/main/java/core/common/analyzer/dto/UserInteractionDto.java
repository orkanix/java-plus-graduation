package core.common.analyzer.dto;

import grpc.telemetry.user_action.ActionTypeProto;
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
public class UserInteractionDto {

    private Long id;

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @Positive
    private Long eventId;

    @NotNull
    private ActionTypeProto actionType;

    @NotNull
    private LocalDateTime timestamp;
}
