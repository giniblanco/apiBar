package atl.academy.security;

import atl.academy.security.filters.JWTAuthenticationFilter;
import atl.academy.security.filters.JWTAuthorizationFilter;
import atl.academy.security.jwt.JWTUtil;
import atl.academy.services.UserDetailServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableMethodSecurity(prePostEnabled = true )
public class SecurityConfig {
    @Autowired
    UserDetailServiceImpl userDetailsService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;
    /**
     *  Este metodo es el encargado de manejar el comportamiento de
     *  acceso a los endpoints y las sesiones de la aplicacion.
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        JWTAuthenticationFilter jwtFilter = new JWTAuthenticationFilter(jwtUtil);
        jwtFilter.setAuthenticationManager(authenticationManager);
        //jwtFilter.setFilterProcessesUrl("/api/login"); Si queremos cambiar la URL del login

        return httpSecurity
                .csrf(config -> config.disable())
                .authorizeHttpRequests(auth -> {
                    //auth.requestMatchers("/swagger-ui/index.html#/").permitAll();
                    auth.anyRequest().permitAll();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); //No manejaremos sesiones.
                })
                //.httpBasic()
                .addFilter(jwtFilter) //Agregamos un filtro jwt de autenticacion.
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class) //Agregamos el filtro de autorizacion que se ejecutara antes del primer filtro(autenticacion)
                .build();
    }

    /*

     *  Este metodo nos servira para crear un usuario en memoria
     *  para probar la autenticacion.
     * @return

    @Bean
    UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("alex")
                .password("12345")
                .roles()
                .build()
        );

        return manager;
    }
    */

    /**
     * Este metodo aplicara encriptacion de la contraseña.
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     *  Este metodo se encarga de la administracion de la
     *  autenticacion de los usuarios.
     * @param httpSecurity
     * @param passwordEncoder Spring Security requiere que nosotros encriptemos las contraseñas.
     * @return
     * @throws Exception
     */
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

}
