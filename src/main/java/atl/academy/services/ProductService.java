package atl.academy.services;

import atl.academy.models.ProductEntity;
import atl.academy.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private IProductRepository productRepository;

    public List<ProductEntity> getAll(){
        return productRepository.findAll();
    }
    public void save(ProductEntity productEntity) {
        productRepository.save(productEntity);
    }
    public boolean getByUsername(String name){
        return getAll().stream()
                .anyMatch(product -> product.getName().equals(name));
    }

    public void delete(long id){
        productRepository.deleteById(id);
    }

    public void deleteAll(){
        productRepository.deleteAll();
    }

    public Optional<ProductEntity> getById(Long id){
        return productRepository.findById(id);
    }
}
