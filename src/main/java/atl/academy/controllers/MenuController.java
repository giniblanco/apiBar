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
    @PutMapping
    public ResponseEntity<Map<String, String>> updateMenu(@RequestBody Menu menu){
        Map<String, String> response = new HashMap<>();

        if(menuService.getById(menu.getId()).isPresent()){
            menuService.save(menu);
            response.put("message", "Menu actualizado correctamente");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("error", "No existe el menu que usted esta intentando actualizar.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable long id){
        Map<String, String> response = new HashMap<>();
        if(menuService.getById(id).isPresent()){
            menuService.delete(id);
            response.put("message", "Menu eliminado correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("error", "No existe el menu que usted desea eliminar");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> borrarTodo(){
        Map<String, String> response = new HashMap<String, String>();
        menuService.deleteAll();
        response.put("message", "Todos los menus fueron eliminados");

        return new ResponseEntity<>(response , HttpStatus.OK);
    }
}
