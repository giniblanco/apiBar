package atl.academy.services;

import atl.academy.models.BarEntity;
import atl.academy.repositories.IBarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarService {

    @Autowired
    private IBarRepository barRepository;

    public void save(BarEntity barEntity){
        if(barExists(barEntity.getName())){
            throw new IllegalArgumentException("Existe un bar con el mismo nombre");
        }
        barRepository.save(barEntity);
    }

    public boolean barExists(String barName){
        return barRepository.existsByName(barName);
    }

    public List<BarEntity> getAll(){
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

    public BarEntity update(Long id, BarEntity barEntity){
        BarEntity existingBarEntity = barRepository.findById(id).orElse(null);

        if(existingBarEntity != null){
            return barRepository.save(barEntity);
        }

        return null;
    }
}
