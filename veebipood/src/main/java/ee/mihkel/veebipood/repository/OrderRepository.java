package ee.mihkel.veebipood.repository;

import ee.mihkel.veebipood.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByPerson_Id(Long id);

    List<Order> findByCreatedBetween(Date createdStart, Date createdEnd);

}
