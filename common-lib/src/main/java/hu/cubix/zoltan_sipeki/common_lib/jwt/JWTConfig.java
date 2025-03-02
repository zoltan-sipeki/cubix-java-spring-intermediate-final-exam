package hu.cubix.zoltan_sipeki.common_lib.jwt;

import java.security.KeyFactory;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.auth0.jwt.algorithms.Algorithm;

@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {

    private long expiration;

    private String issuer;

    private Algorithm verifyingAlgorithm;;

    private Algorithm signingAlgorithm;

    public JWTConfig(Duration expiration, String issuer, String algorithm, String privateKey, String publicKey) throws Exception {
        this.expiration = expiration.toMillis();
        this.issuer = issuer;

        var algorithmMethod = Algorithm.class.getDeclaredMethod(algorithm, RSAKey.class);
        if (privateKey != null) {
            this.signingAlgorithm = (Algorithm) algorithmMethod.invoke(null, getRSAPrivateKey(privateKey));
        }

        if (publicKey != null) {
            this.verifyingAlgorithm = (Algorithm) algorithmMethod.invoke(null, getRSAPublicKey(publicKey));
        }
    }

    public Algorithm getVerifyingAlgorithm() {
        return verifyingAlgorithm;
    }

    public Algorithm getSigningAlgorithm() {
        return signingAlgorithm;
    }

    public Instant getExpiration() {
        return Instant.now().plusMillis(expiration);
    }

    public String getIssuer() {
        return issuer;
    }

    private RSAPrivateKey getRSAPrivateKey(String privateKey) throws Exception{
        var decoded = Base64.getDecoder().decode(privateKey);
        var keySpec = new PKCS8EncodedKeySpec(decoded);

        return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    private RSAPublicKey getRSAPublicKey(String publicKey) throws Exception {
        var publicKeyBytes = Base64.getDecoder().decode(publicKey);
        var keySpec = new X509EncodedKeySpec(publicKeyBytes);

        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }
}
