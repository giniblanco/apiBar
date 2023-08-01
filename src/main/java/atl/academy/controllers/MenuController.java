package atl.academy.controllers;

import atl.academy.models.Menu;
import atl.academy.repositories.MenuRepository;
import atl.academy.services.MenuService;
import atl.academy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }
    private MenuRepository menuRepository;
    private final MenuService menuService;

    @PostMapping("/create")
    public ResponseEntity<String> createMenu(@RequestBody Menu menu) {
        try {
            Menu createdMenu = menuService.createMenu(menu);
            return ResponseEntity.status(HttpStatus.OK).body("Menu created successfully");
        } catch (Exception e) {
            String errorMessage = "Server error";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
    @GetMapping(value = "/menus")
    public List<Menu> getMenus(){
        return menuRepository.findAll();
    }
    @PutMapping(value ="/update/{id}")
    public String updateMenu(@PathVariable long id , @RequestBody Menu menu){
        Menu updateMenu = menuRepository.findById(id).get();
        updateMenu.setName(menu.getName());
        updateMenu.setDescription(menu.getDescription());
        menuRepository.save(updateMenu);
        return "Menu Update";
    }
    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable long id){
        Menu deleteMenu = menuRepository.findById(id).get();
        menuRepository.delete(deleteMenu);
        return "delete";
    }
    /*@DeleteMapping(value = "/delete")
    public String borrarTodo(){
        menuRepository.deleteAll();
        return "delete all";
    }
    */
}