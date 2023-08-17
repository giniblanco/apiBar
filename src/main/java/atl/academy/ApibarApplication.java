package atl.academy;

import atl.academy.models.RoleEntity;
import atl.academy.models.UserEntity;
import atl.academy.models.enums.EROLE;
import atl.academy.repositories.IRoleRepository;
import atl.academy.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class ApibarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApibarApplication.class, args);
	}

	@Autowired
	IUserRepository userRepository;
	@Autowired
	IRoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 *  Se ejecutara antes de iniciar la aplicacion.
	 * @return
	 */
	@Bean
	CommandLineRunner init(){
		return args -> {
			//Creamos 3 roles por defecto en la aplicacion.

			RoleEntity role = RoleEntity.builder()
					.name(EROLE.valueOf(EROLE.ADMIN.name()))
					.build();
			RoleEntity role2 = RoleEntity.builder()
					.name(EROLE.valueOf(EROLE.EMPLOYEE.name()))
					.build();
			RoleEntity role3 = RoleEntity.builder()
					.name(EROLE.valueOf(EROLE.USER.name()))
					.build();

			//Lo guardamos en la base de datos.
			roleRepository.save(role);
			roleRepository.save(role2);
			roleRepository.save(role3);

			//Creamos un usuario por defecto en la aplicacion para poder hacer un login con el mismo.
			UserEntity user = UserEntity.builder()
					.email("asdasdad@gmail.com")
					.username("admin")
					.password(passwordEncoder.encode("admin"))
					.role(role)
					.build();
			//Lo guardamos.
			userRepository.save(user);
		};
	}

}
