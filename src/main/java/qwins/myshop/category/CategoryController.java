package qwins.myshop.category;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qwins.myshop.category.dto.CategoryCreateDTO;
import qwins.myshop.category.dto.CategoryResponseDTO;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> addCategory(@Valid @RequestBody CategoryCreateDTO categoryDTO) {
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .allowedAttributes(categoryDTO.getAllowedAttributes())
                .build();

        Category newCategory = categoryService.addCategory(category);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapToResponseDTO(newCategory));
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(Pageable pageable) {
        Page<CategoryResponseDTO> dtoPage = categoryService.getAllCategories(pageable).map(this::mapToResponseDTO);
        return ResponseEntity.ok(dtoPage);
    }

    private CategoryResponseDTO mapToResponseDTO(Category category) {
        return new CategoryResponseDTO(category);
    }
}
