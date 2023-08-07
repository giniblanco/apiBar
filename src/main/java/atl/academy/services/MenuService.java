package atl.academy.services;

import atl.academy.models.Menu;
import atl.academy.repositories.IMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    @Autowired
    private IMenuRepository menuRepository;

    public List<Menu> getAll(){
        List<Menu> allMenus = new ArrayList<>();
        menuRepository.findAll().forEach(allMenus::add);

        return allMenus;
    }
    public void save(Menu menu) {
        menuRepository.save(menu);
    }
    public boolean getByUsername(String name){
        return getAll().stream()
                .anyMatch(menu -> menu.getName().equals(name));
    }
}
