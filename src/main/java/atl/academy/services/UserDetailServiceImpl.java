package atl.academy.services;

import atl.academy.models.UserEntity;
import atl.academy.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntityDetails = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Usuario no encontrado en el sistema."));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_".concat(userEntityDetails.getRole().getName().name()));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);

        return new User(
                userEntityDetails.getUsername(),
                userEntityDetails.getPassword(),
                true,
                true,
                true,
                true,
                authorities
        );
    }
}
