package core.common.event.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDto {

    private Float lat;

    private Float lon;
}