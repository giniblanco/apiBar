package atl.academy.services;

import atl.academy.models.RoleEntity;
import atl.academy.repositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    IRoleRepository roleRepository;

    public void save(RoleEntity roleEntity){
        roleRepository.save(roleEntity);
    }
}
