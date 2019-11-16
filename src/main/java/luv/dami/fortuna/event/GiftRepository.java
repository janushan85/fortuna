package luv.dami.fortuna.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GiftRepository extends JpaRepository<Gift, Integer> {

    List<Gift> findAllByEventId(Integer eventId);

    @Modifying
    @Query(value = "update gifts g set g.quantity_left = g.quantity_left - 1 where g.id = ? and g.quantity_left > 0", nativeQuery = true)
    int decreaseQuantityLeft(Integer giftId);
}
