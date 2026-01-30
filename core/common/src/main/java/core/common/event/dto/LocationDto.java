package core.common.event.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    @Column(name = "location_lat", nullable = false)
    private Float lat;

    @Column(name = "location_lon", nullable = false)
    private Float lon;
}