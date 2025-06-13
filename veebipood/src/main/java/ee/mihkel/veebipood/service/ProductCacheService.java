package ee.mihkel.veebipood.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ee.mihkel.veebipood.entity.Product;
import ee.mihkel.veebipood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class ProductCacheService {

    @Autowired
    ProductRepository productRepository;

    private LoadingCache<Long, Product> productCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(10, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<>() {
                        @Override
                        public Product load(Long key) {
                            return productRepository.findById(key).orElseThrow();
                        }
                    });

    public Product getProduct(Long id) throws ExecutionException {
        return productCache.get(id);
    }

    public void deleteFromCache(Long id) {
        productCache.invalidate(id);
    }

    public void emptyCache() {
        productCache.invalidateAll();
    }
}
