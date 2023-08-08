package atl.academy.services;

import atl.academy.models.Menu;
import atl.academy.repositories.IMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    @Autowired
    private IMenuRepository menuRepository;

    public List<Menu> getAll(){
        return menuRepository.findAll();
    }
    public void save(Menu menu) {
        menuRepository.save(menu);
    }
    public boolean getByUsername(String name){
        return getAll().stream()
                .anyMatch(menu -> menu.getName().equals(name));
    }

    public void delete(long id){
        menuRepository.deleteById(id);
    }

    public void deleteAll(){
        menuRepository.deleteAll();
    }

    public Optional<Menu> getById(Long id){
        return menuRepository.findById(id);
    }
}
