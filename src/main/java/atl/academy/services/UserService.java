package atl.academy.services;

import atl.academy.models.UserEntity;
import atl.academy.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;

    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public Optional<UserEntity> getBy(Long id){
        return userRepository.findById(id);
    }
    public List<UserEntity> getAll() {
        return (List<UserEntity>) userRepository.findAll();
    }
}
