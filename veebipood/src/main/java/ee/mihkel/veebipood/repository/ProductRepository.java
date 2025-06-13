package ee.mihkel.veebipood.repository;

import ee.mihkel.veebipood.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByOrderByIdAsc();
}
