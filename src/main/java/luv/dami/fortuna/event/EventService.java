package luv.dami.fortuna.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private EventRepository eventRepository;
    private EventParticipantRepository eventParticipantRepository;
    private GiftRepository giftRepository;

    @Autowired
    public EventService(EventRepository eventRepository,
                        EventParticipantRepository eventParticipantRepository,
                        GiftRepository giftRepository) {

        this.eventRepository = eventRepository;
        this.eventParticipantRepository = eventParticipantRepository;
        this.giftRepository = giftRepository;
    }

    @Transactional
    public EventParticipationResult participate(EventParticipant eventParticipant) {

        // TODO: 이벤트 날짜 검증

        if(!isParticipationForToday(eventParticipant)) {
            return EventParticipationResult.CHANCELESS();
        }

        if(isWon(eventParticipant.getEventId(), eventParticipant.getPhoneNumber())) {
            eventParticipantRepository.save(eventParticipant);

            return EventParticipationResult.LOSER();
        }

        Optional<Gift> winningGift = takeChance(eventParticipant.getEventId(), eventParticipant.isMissionCompleted());
        if(winningGift.isPresent()) {
            int decreaseQuantityLeftCount = giftRepository.decreaseQuantityLeft(winningGift.get().getId());
            if(decreaseQuantityLeftCount > 0) {
                eventParticipant.setWon(true);
                eventParticipant.setGiftId(winningGift.get().getId());
                eventParticipantRepository.save(eventParticipant);

                return EventParticipationResult.WINNER(winningGift.get().getId());
            }
        }

        eventParticipantRepository.save(eventParticipant);

        return EventParticipationResult.LOSER();
    }

    private Optional<Gift> takeChance(Integer eventId, boolean missionCompleted) {
        List<Gift> gifts = giftRepository.findAllByEventId(eventId);

        return gifts.stream()
                .sorted(Comparator.comparing(Gift::getIndex))
                .filter(gift -> gift.takeChance(missionCompleted ? 2 : 1))
                .findFirst();
    }

    private boolean isParticipationForToday(EventParticipant eventParticipant) {

        if(isFirstParticipationForToday(eventParticipant)) {
            return true;
        }

        if(eventParticipant.isMissionCompleted() && isSecondParticipation(eventParticipant)) {
            return true;
        }

        return false;
    }

    private boolean isSecondParticipation(EventParticipant eventParticipant) {
        List<EventParticipant> participations =
                eventParticipantRepository.findAllByEventIdAndPhoneNumber(eventParticipant.getEventId(), eventParticipant.getPhoneNumber());

        return participations.size() < 2;
    }

    private boolean isFirstParticipationForToday(EventParticipant eventParticipant) {
        List<EventParticipant> participations =
                eventParticipantRepository.findAllByEventIdAndPhoneNumber(eventParticipant.getEventId(), eventParticipant.getPhoneNumber());

        LocalDateTime firstTimeForToday = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));

        return !participations.stream()
                .filter(p -> p.getJoined().isAfter(firstTimeForToday))
                .findFirst()
                .isPresent();
    }

    private boolean isWon(Integer eventId, String phoneNumber) {
        List<EventParticipant> participations =
                eventParticipantRepository.findAllByEventIdAndPhoneNumber(eventId, phoneNumber);

        return participations.stream()
                .anyMatch(EventParticipant::isWon);
    }
}
