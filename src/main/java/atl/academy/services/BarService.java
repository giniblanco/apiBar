package atl.academy.services;

import atl.academy.models.Bar;
import atl.academy.repositories.IBarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarService {

    @Autowired
    private IBarRepository barRepository;

    public void save(Bar bar){
        if(barExists(bar.getName())){
            throw new IllegalArgumentException("Existe un bar con el mismo nombre");
        }
        barRepository.save(bar);
    }

    public boolean barExists(String barName){
        return barRepository.existsByName(barName);
    }

    public List<Bar> getAll(){
        return barRepository.findAll();
    }

    public boolean delete(Long id){
        if(barRepository.existsById(id)){
            barRepository.deleteById(id);
            return true;
        } else{
            return false;
        }
    }

    public Bar update(Long id, Bar bar){
        Bar existingBar = barRepository.findById(id).orElse(null);

        if(existingBar != null){
            return barRepository.save(bar);
        }

        return null;
    }
}
