package atl.academy.services;

import atl.academy.models.Category;
import atl.academy.repositories.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }
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
