package telemetry.service.analyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telemetry.service.analyzer.model.UserInteraction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserInteractionRepository extends JpaRepository<UserInteraction, Long> {
    Optional<UserInteraction> findByUserIdAndEventId(Long userId, Long eventId);

    List<UserInteraction> findAllByUserId(Long userId);

    List<UserInteraction> findAllByUserIdAndEventIdIn(Long userId, Collection<Long> eventIds);

    List<UserInteraction> findAllByEventIdIn(List<Long> eventsIdList);
}
