package qwins.myshop.product;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product addProduct(Product product) {
        product.setId(null);
        product.validateAttributes();
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Product with id " + id + " not found"
                        )
                );
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "Product with id " + id + " not found"
                )
        );
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setCategory(productDetails.getCategory());
        return productRepository.save(existingProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Product with id " + id + " not found"
            );
        }
        productRepository.deleteById(id);
    }

}
