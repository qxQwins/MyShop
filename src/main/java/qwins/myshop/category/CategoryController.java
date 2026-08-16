package qwins.myshop.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qwins.myshop.category.dto.CategoryResponseDTO;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> addCategory(@RequestBody Category category) {
        Category newCategory = categoryService.addCategory(category);
        return ResponseEntity.ok(mapToResponseDTO(newCategory));
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(Pageable pageable) {
        Page<Category> categoriesPage = categoryService.getAllCategories(pageable);
        Page<CategoryResponseDTO> dtoPage = categoriesPage.map(this::mapToResponseDTO);
        return ResponseEntity.ok(dtoPage);
    }

    private CategoryResponseDTO mapToResponseDTO(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .attributes(category.getAllowedAttributes())
                .build();
    }
}
