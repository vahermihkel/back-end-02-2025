package ee.mihkel.rendipood.repository;

import ee.mihkel.rendipood.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
