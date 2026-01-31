package core.common.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import core.common.category.dto.CategoryDto;
import core.common.user.dto.UserShortDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {

    private Long id;

    @NotBlank
    private String annotation;

    @NotNull
    @Valid
    private CategoryDto category;

    @PositiveOrZero
    private Long confirmedRequests;

    @NotNull
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    @Valid
    private UserShortDto initiator;

    @NotNull
    private Boolean paid;

    @NotBlank
    private String title;

    private Long views;
}
