package ee.mihkel.veebipood.controller;

import ee.mihkel.veebipood.entity.Category;
import ee.mihkel.veebipood.entity.Product;
import ee.mihkel.veebipood.repository.ProductRepository;
import ee.mihkel.veebipood.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

//    private ProductRepository productRepository;
//
//    public ProductController(ProductRepository _productRepository) {
//        this.productRepository = _productRepository;
//    }

    // localhost:8080/products
    @GetMapping("products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productRepository.findByOrderByIdAsc()); // SELECT * FROM products;
    }

    // uue lisamiseks -> uue postitamiseks
    @PostMapping("products")
    public ResponseEntity<List<Product>> addProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            throw new RuntimeException("ERROR_CANNOT_ADD_WITH_ID");
        }
        if (product.getPrice() <= 0) {
            throw new RuntimeException("ERROR_CANNOT_SET_PRICE_0_OR_SMALLER");
        }
        productRepository.save(product);
        return ResponseEntity.status(201).body(productRepository.findByOrderByIdAsc());
    }

    // asendamiseks -> muutmiseks
    @PutMapping("products")
    public ResponseEntity<List<Product>> editProduct(@RequestBody Product product) {
        if (product.getId() == null) {
            throw new RuntimeException("ERROR_CANNOT_EDIT_WITHOUT_ID");
        }
        if (product.getPrice() <= 0) {
            throw new RuntimeException("ERROR_CANNOT_SET_PRICE_0_OR_SMALLER");
        }
        productService.updateProduct(product);
        return ResponseEntity.ok(productRepository.findByOrderByIdAsc());
    }

    // PATCH   localhost:8080/product-active?id=4&active=true
    @PatchMapping("product-active")
    public ResponseEntity<List<Product>> editProductActive(@RequestParam Long id, boolean active) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setActive(active);
        productRepository.save(product);
        return ResponseEntity.ok(productRepository.findByOrderByIdAsc());
    }

    @PatchMapping("product-price")
    public ResponseEntity<List<Product>> editProductPrice(@RequestParam Long id, double price) {
        if (price <= 0) {
            throw new RuntimeException("ERROR_CANNOT_SET_PRICE_0_OR_SMALLER");
        }
        Product product = productRepository.findById(id).orElseThrow();
        product.setPrice(price);
        productRepository.save(product);
        return ResponseEntity.ok(productRepository.findByOrderByIdAsc());
    }

    @PatchMapping("products")
    public ResponseEntity<List<Product>> editProductByPatch(@RequestParam Long id, String field, String value) {
        Product product = productRepository.findById(id).orElseThrow();
        switch (field) {
            case "price" -> {
                if (Double.parseDouble(value) <= 0) {
                    throw new RuntimeException("ERROR_CANNOT_SET_PRICE_0_OR_SMALLER");
                }
                product.setPrice(Double.parseDouble(value));
            }
            case "active" -> product.setActive(Boolean.parseBoolean(value));
            case "image" -> product.setImage(value);
            case "name" -> product.setName(value);
            case "category" -> product.setCategory(new Category(Long.parseLong(value),"",true));
            case null, default -> throw new RuntimeException();
        }
        productRepository.save(product);
        return ResponseEntity.ok(productRepository.findByOrderByIdAsc());
    }

    // DELETE localhost:8080/products/1   <-- @PathVariable
    // DELETE localhost:8080/products?id=1   <--  @RequestParam
    @DeleteMapping("products/{id}")
    public ResponseEntity<List<Product>> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @GetMapping("products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) throws InterruptedException {
//        Thread.sleep(1000);
        return ResponseEntity.ok(productService.getProduct(id));
    }
}

//harvad: 1xx --> info
// 2xx --> õnnestuvad
//harvad: 3xx --> redirect
// 4xx --> päringu tegija viga -> client error
// 400 -> üldine
// 401,403 -> autentimine
// 402 -> maksete jaoks

// 404 -> API endpointi ei leitud
// 405 -> method on vale
// 415 -> Body tüüp on vale (JSON asemel text)

// 5xx --> back-endi viga -> server error