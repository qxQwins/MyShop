package qwins.myshop.category;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class CategoryService {

    CategoryRepository categoryRepository;

    CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Category doesn't exist")
                );
    }

    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Transactional
    public Category addCategory(Category category) {
        category.setId(null);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, Category categoryDetails) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Category doesn't exist")
                );
        existing.setName(categoryDetails.getName());
        existing.setAllowedAttributes(categoryDetails.getAllowedAttributes());
        return categoryRepository.save(existing);
    }

    public void deleteCategory(Long id) {
        if(!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category doesn't exist");
        }
        categoryRepository.deleteById(id);
    }
}
