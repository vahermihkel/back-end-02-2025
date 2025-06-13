package ee.mihkel.veebipood.controller;

import ee.mihkel.veebipood.entity.Category;
import ee.mihkel.veebipood.entity.Product;
import ee.mihkel.veebipood.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PostMapping("categories")
    public ResponseEntity<List<Category>> addCategories(@RequestBody Category category) {
        if (category.getId() != null) {
            throw new RuntimeException("ERROR_CANNOT_ADD_WITH_ID");
        }
        if (category.getName().length() < 3) {
            throw new RuntimeException("ERROR_CATEGORY_TOO_SHORT");
        }
        categoryRepository.save(category);
        return ResponseEntity.status(201).body(categoryRepository.findAll());
    }

    @PutMapping("categories")
    public ResponseEntity<List<Category>> editCategory(@RequestBody Category category) {
        if (category.getId() == null) {
            throw new RuntimeException("ERROR_CANNOT_EDIT_WITHOUT_ID");
        }
        categoryRepository.save(category);
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @DeleteMapping("categories/{id}")
    public ResponseEntity<List<Category>> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PatchMapping("category-active")
    public ResponseEntity<List<Category>> editCategoryActive(@RequestParam Long id, boolean active) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setActive(active);
        categoryRepository.save(category);
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("categories/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryRepository.findById(id).orElseThrow());
    }
}
