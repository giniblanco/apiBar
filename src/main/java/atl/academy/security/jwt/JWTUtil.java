package atl.academy.security.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JWTUtil {
        @Value("${jwt.secret.key}")private String secretKey; //Clave secreta o firma de nuestro token.
        @Value("${jwt.time.expiration}")private String timeExpiration;

        /**
         * Este metodo obtendra la firma del token en un
         * objeto Key.
         */
        public Key getSignatureKey(){
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        }

        /**
         * Este metodo generara nuestro token.
         */
        public String generateAccessToken(String username){
            return Jwts.builder()
                    .setSubject(username) //Sujeto quien crea el token.
                    .setIssuedAt(new Date(System.currentTimeMillis())) //La fecha en milisegundos del token creado.
                    .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration))) //La fecha de expiracion
                    .signWith(getSignatureKey(), SignatureAlgorithm.HS256) //Indicamos la firma de nuestro metodo con un algoritmo de encriptacion.
                    .compact();
        }


        /**
         *  Este metodo validara el token de acceso
         */
        public boolean isTokenValid(String token){
            try{
                //Decodificamos el token para leerlo.
                Jwts.parserBuilder()
                        .setSigningKey(getSignatureKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                return true;
            }catch (Exception e){
                log.error("Token not valid: "+e.getMessage());
                return false;
            }
        }

        //Este meotodo obtendra todos los claims del token
        public Claims extractAllClaims(String token){
            return Jwts.parserBuilder() //Decodificamos el token para leerlo.
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }

        //Obtener un solo Claim
        public <T> T getClaim(String token, Function<Claims, T> claimsTFunction){
            Claims allClaims = extractAllClaims(token);
            return claimsTFunction.apply(allClaims);
        }

        /**
         *  Obtenemos el sujeto quien creo el Token.
         * @param token
         * @return
         */
        public String getUsernameFromToken(String token){
            return getClaim(token, Claims::getSubject);
        }
}
