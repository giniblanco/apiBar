package atl.academy.controllers;

import atl.academy.models.Menu;
import atl.academy.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getMenus(){
        Map<String, Object> response = new HashMap<>();
        var allMenus = menuService.getAll();
        if(allMenus.isEmpty()){
            response.put("message", "No existen menus actualmente en base de datos.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }else{
            response.put("menus", allMenus);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> createMenu(@RequestBody Menu menu) {
        Map<String, Object> response = new HashMap<>();

        if(menuService.getByUsername(menu.getName())){
            response.put("error", "Menu ya existente.");

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }else{
            menuService.save(menu);
            response.put("message", "Menu creado correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
    /*
    @PutMapping(value ="/update/{id}")
    public String updateMenu(@PathVariable long id , @RequestBody Menu menu){
        Menu updateMenu = IMenuRepository.findById(id).get();
        updateMenu.setName(menu.getName());
        updateMenu.setDescription(menu.getDescription());
        IMenuRepository.save(updateMenu);
        return "Menu Update";
    }
    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable long id){
        Menu deleteMenu = IMenuRepository.findById(id).get();
        IMenuRepository.delete(deleteMenu);
        return "delete";
    }
    @DeleteMapping(value = "/delete")
    public String borrarTodo(){
        menuRepository.deleteAll();
        return "delete all";
    }
     */
}
