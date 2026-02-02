package core.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import core.requests.model.Request;

import java.util.List;
import java.util.Set;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRequester(Long userId);

    List<Request> findAllByEvent(Long eventId);

    List<Request> findAllByIdIn(Set<Long> requestIds);

    boolean existsByEventAndRequester(Long eventId, Long userId);
}
