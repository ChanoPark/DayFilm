package com.rabbit.dayfilm;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@SpringBootTest
@ActiveProfiles("dev")
public class EncryptionTest {

    private String privateKey;
    private String publicKey;
    private static final String ALGORITHM = "RSA"; 

    @Test
    public void encrypt_decrypt_test() {
        String plainText = "test-plain!";

        keyGenerator();
        Assertions.assertThat(privateKey).isNotNull();
        Assertions.assertThat(publicKey).isNotNull();

        String encryptedText = encrypt(plainText);
        String decryptedText = decrypt(encryptedText);
        Assertions.assertThat(plainText).isEqualTo(decryptedText);
    }

    public void keyGenerator() {
        SecureRandom secureRandom = new SecureRandom();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(1024, secureRandom);
            KeyPair keyPair = keyPairGenerator.genKeyPair();

            PrivateKey privateKeyToEncode = keyPair.getPrivate();
            PublicKey publicKeyToEncode = keyPair.getPublic();

            publicKey = Base64.getEncoder().encodeToString(publicKeyToEncode.getEncoded());
            privateKey = Base64.getEncoder().encodeToString(privateKeyToEncode.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String plain) {
        String encryptedText = null;
        try {
            //평문으로 전달받은 공개키를 사용하기 위한 공개키 객체 생성 (String -> PublicKey)
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            byte[] bytePublicKey = Base64.getDecoder().decode(publicKey.getBytes());
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytePublicKey);
            PublicKey getPublicKey = keyFactory.generatePublic(publicKeySpec);

            // 공개키 객체를 통한 암호화
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey);

            byte[] encryptBytes = cipher.doFinal(plain.getBytes());
            encryptedText = Base64.getEncoder().encodeToString(encryptBytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException |
                 IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return encryptedText;
    }

    public String decrypt(String encryptedText) {
        String decryptedText = null;

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
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException e) {
        }
        return decryptedText;
    }
}
