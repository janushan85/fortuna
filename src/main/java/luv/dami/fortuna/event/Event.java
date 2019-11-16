package luv.dami.fortuna.event;

import lombok.Getter;
import lombok.Setter;
import luv.dami.fortuna.model.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "begin_date", nullable = false)
    private LocalDate beginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
}
