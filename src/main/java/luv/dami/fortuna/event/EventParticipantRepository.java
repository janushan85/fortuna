package luv.dami.fortuna.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, Integer> {

    List<EventParticipant> findAllByEventIdAndPhoneNumber(Integer eventId, String phoneNumber);
}
