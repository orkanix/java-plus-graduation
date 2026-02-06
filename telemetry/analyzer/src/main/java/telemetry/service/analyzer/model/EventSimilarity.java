package telemetry.service.analyzer.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "similarities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EventSimilarity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long eventA;

    @Column(nullable = false)
    private Long eventB;

    @Column(nullable = false)
    private Double score;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
