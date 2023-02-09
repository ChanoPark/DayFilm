package com.rabbit.dayfilm.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.exception.FilterException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Date;
import java.time.Instant;
import java.util.Base64;

@Component
public class AuthUtil {
    private static String SECRET_KEY;
    private static String ISSUER;
    private static String RSA_PUBLIC_KEY;
    private static String RSA_PRIVATE_KEY;
    private static final String ALGORITHM = "RSA";

    private static final String TYPE = "type";
    private static final long ACCESS_TIME = 60 * 3600;  // 액세스 토큰 6시간
    private static final long REFRESH_TIME = 60 * 60 * 24 * 55;  // 리프레시 토큰 약 2달

    @Value("${secure.jwt.secret_key}")
    public void setSecretKey(String secretKey) {
        AuthUtil.SECRET_KEY = secretKey;
    }

    @Value("${secure.jwt.issuer}")
    public void setIssuer(String issuer) {
        AuthUtil.ISSUER = issuer;
    }

    @Value("${secure.RSA.publicKey}")
    public void setRsaPublicKey(String publicKey) {
        AuthUtil.RSA_PUBLIC_KEY = publicKey;
    }

    @Value("${secure.RSA.privateKey}")
    public void setRsaPrivateKey(String privateKey) {
        AuthUtil.RSA_PRIVATE_KEY = privateKey;
    }

    public static String createAccessToken(String email, String privateKey, String encryptedPw) {
        final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(Date.from(Instant.now().plusSeconds(ACCESS_TIME)))
                .withIssuedAt(Date.from(Instant.now()))
                .withNotBefore(Date.from(Instant.now()))
                .withSubject(email)
                .withClaim("secret_key", privateKey)
                .withClaim("code", encryptedPw)
                .withClaim(TYPE, "access")
                .sign(ALGORITHM);
    }

    /*Refresh token provider*/
    public static String createRefreshToken(String email) {
        final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(Date.from(Instant.now().plusSeconds(REFRESH_TIME)))
                .withNotBefore(Date.from(Instant.now()))
                .withSubject(email)
                .withClaim(TYPE, "refresh")
                .sign(ALGORITHM);
    }

    public static boolean accessVerify(String token) {
        final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

        DecodedJWT verify;
        try {
            verify = JWT.require(ALGORITHM).build().verify(token);
        } catch (IllegalArgumentException | JWTVerificationException e) {
            throw new CustomException(e.getMessage());
        }

        if (!verify.getIssuer().equals(ISSUER)) throw new CustomException("ISS_INVALID");
        else if (verify.getExpiresAt().before(Date.from(Instant.now()))) throw new CustomException("EXP_INVALID");
        else if (verify.getNotBefore().after(Date.from(Instant.now()))) throw new CustomException("NBF_INVALID");

        return true;
    }

    public static boolean refreshVerify(String token) {
        final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

        DecodedJWT verify;
        try {
            verify = JWT.require(ALGORITHM).build().verify(token);
        } catch (IllegalArgumentException | JWTVerificationException e) {
            throw new CustomException(e.getMessage());
        }

        if (!verify.getIssuer().equals(ISSUER)) throw new CustomException("ISS_INVALID");
        else if (verify.getNotBefore().after(Date.from(Instant.now()))) throw new CustomException("NBF_INVALID");

        return true;
    }

    public static String encrypt(String pw, String publicKey) {
        String encryptedPw;

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            byte[] bytePublicKey = Base64.getDecoder().decode(publicKey.getBytes());
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytePublicKey);
            PublicKey getPublicKey = keyFactory.generatePublic(publicKeySpec);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey);

            byte[] encryptBytes = cipher.doFinal(pw.getBytes());
            encryptedPw = Base64.getEncoder().encodeToString(encryptBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new FilterException(CodeSet.INVALID_ALGORITHM);
        } catch (InvalidKeySpecException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new FilterException(CodeSet.INVALID_KEY);
        }

        return encryptedPw;
    }

    public static String decrypt(String encryptedText) {
        return null;
    }

    public static RSAKey generateKey() {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new FilterException(CodeSet.INVALID_ALGORITHM);
        }

        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();

        PrivateKey privateKeyToEncode = keyPair.getPrivate();
        PublicKey publicKeyToEncode = keyPair.getPublic();

        String publicKey = Base64.getEncoder().encodeToString(publicKeyToEncode.getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(privateKeyToEncode.getEncoded());

        return new RSAKey(privateKey, publicKey);
    }
}
