package ee.mihkel.veebipood.service;

import ee.mihkel.veebipood.entity.Product;
import ee.mihkel.veebipood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Cacheable(value = "productCache", key = "#id")
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @CacheEvict(value = "productCache", key = "#id")
    public List<Product> deleteProduct(Long id) {
        productRepository.deleteById(id);
        return productRepository.findByOrderByIdAsc();
    }

    @CachePut(value = "productCache", key = "#product.id")
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
}
