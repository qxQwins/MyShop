package qwins.myshop.product;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.PrioritizedParameterNameDiscoverer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qwins.myshop.category.Category;
import qwins.myshop.category.CategoryRepository;
import qwins.myshop.product.dto.ProductCreateDTO;
import qwins.myshop.product.dto.ProductResponseDTO;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    private final CategoryRepository categoryRepository;

    public ProductController(ProductService productService,
                             CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductCreateDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(
                () -> new EntityNotFoundException("Category not found")
        );

        Product product = productService.addProduct(
                Product.builder()
                        .name(productDTO.getName())
                        .description(productDTO.getDescription())
                        .price(productDTO.getPrice())
                        .category(category)
                        .attributes(productDTO.getAttributes())
                        .build()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponseDTO(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(new ProductResponseDTO(product));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(Pageable pageable) {
        Page<Product> productsPage = productService.getAllProducts(pageable);
        Page<ProductResponseDTO> dtoPage = productsPage.map(
                product -> new ProductResponseDTO(product)
        );
        return ResponseEntity.ok(dtoPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct
            (@PathVariable Long id,
             @RequestBody Product updatedProduct) {
        productService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok(new ProductResponseDTO(updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id
    ) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
