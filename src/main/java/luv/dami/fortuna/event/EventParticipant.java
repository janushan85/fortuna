package luv.dami.fortuna.event;

import lombok.Getter;
import lombok.Setter;
import luv.dami.fortuna.model.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "event_participants")
public class EventParticipant extends BaseEntity {

    @Column(name = "event_id", nullable = false)
    private Integer eventId;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "mission1")
    private boolean mission1 = false;

    @Column(name = "mission2")
    private boolean mission2 = false;

    @Column(name = "won")
    private boolean won = false;

    @Column(name = "gift_id")
    private Integer giftId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "joined")
    private LocalDateTime joined = LocalDateTime.now();

    public boolean isMissionCompleted() {
        return mission1 && mission2;
    }
}
