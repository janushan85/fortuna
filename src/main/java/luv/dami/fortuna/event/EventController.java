package luv.dami.fortuna.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    private EventService eventService;
    private EventRepository eventRepository;

    @Autowired
    public EventController(EventService eventService,
                           EventRepository eventRepository) {

        this.eventService = eventService;
        this.eventRepository = eventRepository;
    }

    @PostMapping("/events")
    public void save(Event event) {
        eventRepository.save(event);
    }

    @PostMapping("/events/{eventId}/participate")
    public ResponseEntity<EventParticipationResult> participate(@PathVariable Integer eventId, @RequestBody EventParticipant eventParticipant) {

        eventParticipant.setEventId(eventId);

        return new ResponseEntity<>(eventService.participate(eventParticipant), HttpStatus.OK);
    }
}
