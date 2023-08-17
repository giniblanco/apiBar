package atl.academy.services;

import atl.academy.models.CategoryEntity;
import atl.academy.repositories.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    public List<CategoryEntity> getAll(){
        return categoryRepository.findAll();
    }
    public CategoryEntity save(CategoryEntity categoryEntity) {
       if (categoryExists(categoryEntity.getName())) {
           throw new IllegalArgumentException("Existe una categoria con el mismo nombre");
       }
       return categoryRepository.save(categoryEntity);
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

    public Optional<CategoryEntity> getById(Long id){
        return categoryRepository.findById(id);
    }

    public CategoryEntity updateCategory(Long id, CategoryEntity categoryEntity){

        CategoryEntity existingCategoryEntity = categoryRepository.findById(id).orElse(null);

        if(existingCategoryEntity != null){
            return categoryRepository.save(categoryEntity);
        }

        return null;
    }
}
