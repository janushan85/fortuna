package luv.dami.fortuna.event;

import lombok.Getter;
import lombok.Setter;
import luv.dami.fortuna.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "gifts")
public class Gift extends BaseEntity {

    @Column(name = "index")
    private int index;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "quantityLeft")
    private int quantityLeft;

    @Column(name = "chance_to_win")
    private double chanceToWin;

    public boolean takeChance(double up) {
        if(this.quantity < 1) {
            return false;
        }

        return Math.random() <= this.chanceToWin * up;
    }
}
