package atl.academy.services;

import atl.academy.models.Category;
import atl.academy.repositories.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;
    public Category save(Category category) {
       if (categoryExists(category.getName())) {
           throw new IllegalArgumentException("Existe una categoria con el mismo nombre");
       }
       return categoryRepository.save(category);
    }
    public boolean categoryExists(String categoryName) {
        return categoryRepository.existsByName(categoryName);
    }
}
