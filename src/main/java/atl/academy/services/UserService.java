package atl.academy.services;

import atl.academy.models.User;
import atl.academy.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;


    public void registerUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User update(Long id, User user){
        User existingUser = userRepository.findById(id).orElse(null);

        if(existingUser != null){
            return userRepository.save(user);
        }

        return null;
    }
}
