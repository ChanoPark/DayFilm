package com.rabbit.dayfilm.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.exception.FilterException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Date;
import java.time.Instant;
import java.util.Base64;

import static com.rabbit.dayfilm.common.CodeSet.ACCESS_TOKEN_INVALID;
import static com.rabbit.dayfilm.common.CodeSet.REFRESH_TOKEN_INVALID;

@Component
public class AuthUtil {
    private static String SECRET_KEY;
    private static String ISSUER;
    private static final String ALGORITHM = "RSA";

    private static final String TYPE = "type";
    private static final String SECRET_KEY_IN_TOKEN = "secret_key";
    private static final String ACCESS = "access";
    private static final String REFRESH = "refresh";
    private static final String CODE = "code";
    private static final long ACCESS_TIME = 60 * 3600;  // 액세스 토큰 6시간
    private static final long REFRESH_TIME = 60 * 60 * 24 * 55;  // 리프레시 토큰 약 2달

    @Value("${secure.jwt.secretKey}")
    public void setSecretKey(String secretKey) {
        AuthUtil.SECRET_KEY = secretKey;
    }

    @Value("${secure.jwt.issuer}")
    public void setIssuer(String issuer) {
        AuthUtil.ISSUER = issuer;
    }

    public static String createAccessToken(String email, String privateKey, String encryptedPw) {
        final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(Date.from(Instant.now().plusSeconds(ACCESS_TIME)))
                .withIssuedAt(Date.from(Instant.now()))
                .withNotBefore(Date.from(Instant.now()))
                .withSubject(email)
                .withClaim(SECRET_KEY_IN_TOKEN, privateKey)
                .withClaim(CODE, encryptedPw)
                .withClaim(TYPE, ACCESS)
                .sign(ALGORITHM);
    }

    /*Refresh token provider*/
    public static String createRefreshToken(String email, String privateKey) {
        final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(Date.from(Instant.now().plusSeconds(REFRESH_TIME)))
                .withNotBefore(Date.from(Instant.now()))
                .withSubject(email)
                .withClaim(TYPE, REFRESH)
                .withClaim(SECRET_KEY_IN_TOKEN, privateKey)
                .sign(ALGORITHM);
    }

    public static boolean accessVerify(String token) {
        final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

        DecodedJWT verify;
        try {
            verify = JWT.require(ALGORITHM).build().verify(token);
        } catch (IllegalArgumentException | JWTVerificationException e) {
            throw new FilterException(ACCESS_TOKEN_INVALID);
        }

        if (!verify.getIssuer().equals(ISSUER)
                || verify.getNotBefore().after(Date.from(Instant.now()))
                || verify.getSubject().isEmpty()
                || verify.getClaim(SECRET_KEY_IN_TOKEN).isNull()
                || verify.getClaim(CODE).isNull()
                || !verify.getClaim(TYPE).asString().equals(ACCESS))
            throw new FilterException(ACCESS_TOKEN_INVALID);
        else if (verify.getExpiresAt().before(Date.from(Instant.now())))
            throw new FilterException(CodeSet.ACCESS_TOKEN_EXPIRED);

        return true;
    }

    public static boolean refreshVerify(String token) {
        final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

        DecodedJWT verify;
        try {
            verify = JWT.require(ALGORITHM).build().verify(token);
        } catch (IllegalArgumentException | JWTVerificationException e) {
            throw new FilterException(REFRESH_TOKEN_INVALID);
        }

        if (!verify.getIssuer().equals(ISSUER)
                || !verify.getClaim(TYPE).asString().equals(REFRESH)
                || verify.getNotBefore().after(Date.from(Instant.now()))
                || verify.getClaim(SECRET_KEY_IN_TOKEN).isNull())
            throw new FilterException(REFRESH_TOKEN_INVALID);
        else if (verify.getExpiresAt().before(Date.from(Instant.now())))
            throw new FilterException(CodeSet.REFRESH_TOKEN_EXPIRED);

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

    public static String decrypt(String encryptedText, String privateKey) {
        String decryptedText;

        try {
            //평문으로 전달받은 개인키를 사용하기 위한 개인키 객체 생성 (String -> PrivateKey)
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            byte[] bytePrivateKey = Base64.getDecoder().decode(privateKey.getBytes());
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytePrivateKey);
            PrivateKey getPrivatekey = keyFactory.generatePrivate(privateKeySpec);

            //개인키 객체로 복호화 설정
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getPrivatekey);

            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText.getBytes());
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            decryptedText = new String(decryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new FilterException(CodeSet.INVALID_ALGORITHM);
        } catch (IllegalBlockSizeException | InvalidKeySpecException | BadPaddingException | InvalidKeyException e) {
            throw new FilterException(CodeSet.INVALID_KEY);
        }

        return decryptedText;
    }

    public static RSAKey generateKey() {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator;
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
