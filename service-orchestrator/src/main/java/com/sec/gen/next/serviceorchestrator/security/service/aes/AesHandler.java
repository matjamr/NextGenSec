package com.sec.gen.next.serviceorchestrator.security.service.aes;

import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Getter
@Component
public final class AesHandler implements CommandLineRunner {

    private static IvParameterSpec ivParameterSpec;
    private static SecretKey secret;

    @Override
    public void run(String... args) throws Exception {
        ivParameterSpec = getFixedIv();
        secret = getKeyFromPassword("fixed-password", "fixed-salt");
    }

    @SneakyThrows
    public static byte[] encrypt(byte[] input) {
        return encrypt("AES/CBC/PKCS5Padding", input, secret, ivParameterSpec);
    }

    @SneakyThrows
    public static byte[] decrypt(byte[] input) {
        return decrypt("AES/CBC/PKCS5Padding", input, secret, ivParameterSpec);
    }

    public static byte[] encrypt(String algorithm, byte[] input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input);
        return Base64.getEncoder().encode(cipherText);
    }

    public static byte[] decrypt(String algorithm, byte[] cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return plainText;
    }

    public static SecretKey getKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return secret;
    }

    public static IvParameterSpec getFixedIv() {
        byte[] iv = new byte[16];
        return new IvParameterSpec(iv);
    }
}
