package atl.academy.services;

import atl.academy.models.Bar;
import atl.academy.models.Category;
import atl.academy.models.Product;
import atl.academy.repositories.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void delete(long id){
        categoryRepository.deleteById(id);
    }

    public void deleteAll(){
        categoryRepository.deleteAll();
    }

    public Optional<Category> getById(Long id){
        return categoryRepository.findById(id);
    }

    public Category updateCategory(Long id, Category category){

        Category existingCategory = categoryRepository.findById(id).orElse(null);

        if(existingCategory != null){
            return categoryRepository.save(category);
        }

        return null;
    }
}
