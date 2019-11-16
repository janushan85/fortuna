package luv.dami.fortuna.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventParticipationResult {

    private EventResult result;
    private Integer giftId;

    private EventParticipationResult() {}

    private EventParticipationResult(EventResult result) {
        this.result = result;
    }

    private EventParticipationResult(EventResult result, Integer giftId) {
        this.result = result;
        this.giftId = giftId;
    }

    public static EventParticipationResult WINNER(Integer giftId) {
        return new EventParticipationResult(EventResult.WIN, giftId);
    }

    public static EventParticipationResult LOSER() {
        return new EventParticipationResult(EventResult.LOSE);
    }

    public static EventParticipationResult CHANCELESS() {
        return new EventParticipationResult(EventResult.NO_CHANCE);
    }
}
