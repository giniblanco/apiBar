package atl.academy.security.filters;
import atl.academy.models.UserEntity;
import atl.academy.security.jwt.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private JWTUtil jwtUtil;
    public JWTAuthenticationFilter(JWTUtil jwt){
        this.jwtUtil = jwt;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        UserEntity user = null;
        String username = "";
        String password = "";

        try{
            user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);

            username = user.getUsername();
            password = user.getPassword();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Si salio bien, tendremos el username y password del usuario en cuestion para autenticarnos.
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password);


        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal(); //Obtenemos los detalles del usuario autenticado.
        String token = jwtUtil.generateAccessToken(user.getUsername());

        response.addHeader("Authorization", token);
        Map<String, Object> httpResponse = new LinkedHashMap<>();

        httpResponse.put("message", "Autenticacion correcta.");
        httpResponse.put("user", user.getUsername());
        httpResponse.put("role", user.getAuthorities());
        httpResponse.put("token", token);

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();


        super.successfulAuthentication(request, response, chain, authResult);
    }
}
