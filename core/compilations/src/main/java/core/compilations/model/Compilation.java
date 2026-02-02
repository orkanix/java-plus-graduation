package core.compilations.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compilations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "compilation_events",
            joinColumns = @JoinColumn(name = "compilation_id"), foreignKey = @ForeignKey(name = "fk_compilation_events__compilations"))
    @Column(name = "event_id")
    @Builder.Default
    private List<Long> events = new ArrayList<>();

    @Column(nullable = false)
    private Boolean pinned;

    @Column(nullable = false)
    private String title;
}
