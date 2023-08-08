package atl.academy.services;

import atl.academy.models.Product;
import atl.academy.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private IProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }
    public void save(Product product) {
        productRepository.save(product);
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

    public Optional<Product> getById(Long id){
        return productRepository.findById(id);
    }
}
