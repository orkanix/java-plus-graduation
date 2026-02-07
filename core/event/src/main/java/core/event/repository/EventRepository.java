package core.event.repository;

import core.common.event.dto.EventState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import core.event.model.Event;

import java.util.*;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {

    Page<Event> findAllByInitiator(Long userId, Pageable pageable);

    Optional<Event> findByIdAndInitiator(Long eventId, Long userId);

    Optional<Event> findByIdAndState(Long eventId, EventState state);

    boolean existsByCategory(Long categoryId);

    boolean existsByIdAndInitiator(Long eventId, Long userId);

    List<Event> findAllByIdIn(Collection<Long> ids);
}