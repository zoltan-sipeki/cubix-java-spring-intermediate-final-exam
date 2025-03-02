package hu.cubix.zoltan_sipeki.common_lib.jwt;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTService {

    private JWTConfig jwtConfig;

    private JWTVerifier verifier;

    public JWTService(JWTConfig jwtConfig) {
        this.jwtConfig = jwtConfig;

        var algorithm = jwtConfig.getVerifyingAlgorithm();
        if (algorithm != null) {
            this.verifier = JWT.require(algorithm).build();
        }
    }

    public String createToken(User user) {
        if (jwtConfig.getSigningAlgorithm() == null) {
            throw new IllegalStateException("JWT signing algorithm is not configured");
        }

        var builder = JWT.create();
        builder.withSubject(user.getUsername());
        builder.withIssuer(jwtConfig.getIssuer());
        builder.withArrayClaim("roles",
                user.getAuthorities().stream().map(a -> a.getAuthority()).toArray(String[]::new));
        builder.withExpiresAt(jwtConfig.getExpiration());
        return builder.sign(jwtConfig.getSigningAlgorithm());
    }

    public User parseToken(String token) {
        var decodedJWT = verifyToken(token);
        if (decodedJWT == null) {
            return null;
        }
        
        var username = decodedJWT.getSubject();
        var roles = decodedJWT.getClaim("roles").asList(String.class).stream().map(SimpleGrantedAuthority::new)
                .toList();
        return new User(username, "", roles);
    }

    public DecodedJWT verifyToken(String token) {
        if (verifier == null) {
            throw new IllegalStateException("JWT verifier is not configured");
        }

        try {
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
