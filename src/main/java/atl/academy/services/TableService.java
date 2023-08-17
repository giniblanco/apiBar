package atl.academy.services;

import atl.academy.models.TableEntity;
import atl.academy.repositories.ITableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TableService {
    @Autowired
    ITableRepository tableRepository;

    public List<TableEntity> getTables(){
        return tableRepository.findAll();
    }
    public void saveTable(TableEntity tableEntity){
        tableRepository.save(tableEntity);
    }
    public void deleteTable(Long id){
        tableRepository.deleteById(id);
    }
    public Optional<TableEntity> getBy(Long id) {
        return tableRepository.findById(id);
    }
    public Optional<TableEntity> getBy(Integer numberTable) {
        return getTables().stream().
                                    filter(t -> t.getNumberTable()
                                            .equals(numberTable))
                .findFirst();
    }

}
