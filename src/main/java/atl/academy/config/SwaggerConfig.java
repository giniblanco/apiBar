package atl.academy.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Team2 - ATL Academy",
                        email = "team2@atlacademy.com"
                ),
                description = "API BAR - Documentation for API developed on SpringBoot.",
                title = "API BAR - Team2",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Localhost - DEV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Production - DEV",
                        url = "Currently no URL"
                )
        }
        //security = @SecurityRequirement(name = "bearerAuth") //Si queremos que todos nuestros endpoints esteen asegurados, indicamos de esta manera.
)
//Esta es la configuracion que permitira agregar una autenticacion a nuestra API
@SecurityScheme(
        name = "bearerAuth", //Indicamos el nombre del esquema
        description = "JWT Authorization", //Una descripcion
        scheme = "bearer",
        type = SecuritySchemeType.HTTP, //Indicamos de que tipo va hacer la autenticacion
        bearerFormat = "JWT", //El formato
        in = SecuritySchemeIn.HEADER //Donde se inyectara el JWT, en este caso en la cabecera del request.
)
public class SwaggerConfig { }