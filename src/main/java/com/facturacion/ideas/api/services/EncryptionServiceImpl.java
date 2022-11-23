package com.facturacion.ideas.api.services;


import com.facturacion.ideas.api.exeption.EncryptedException;
import org.springframework.stereotype.Service;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class EncryptionServiceImpl implements IEncryptionService {

    private final String ALGORITMO = "AES"; // tipo de algoritmo

    // Tiene que tener 16 caracteres, 16 bytes
    private final String KEY_SECRET = "desarrollando015";

    private final String CIFRADO = "AES/CBC/PKCS5Padding";

    // Tiene que tener 16 caracteres, 16 bytes
    private final String INICIALIZACION = "0123456789ABCDEF";

    @Override
    public String encrypt(String textPlane) {

        try {

            Cipher cipher = Cipher.getInstance(CIFRADO);

            // Llave secreta
            SecretKeySpec secretKeySpec = new SecretKeySpec(KEY_SECRET.getBytes(), ALGORITMO);

            // Parametros
            IvParameterSpec ivParameterSpec = new IvParameterSpec(INICIALIZACION.getBytes());

            // Cifrar constraseña
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] escrypted = cipher.doFinal(textPlane.getBytes());

            return Base64.getEncoder().encodeToString(escrypted);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new EncryptedException("Error al encriptar contraseña certificado");

        }
    }

    @Override
    public String deEncrypt(String textEncrypt) {

        try {

            Cipher cipher = Cipher.getInstance(CIFRADO);

            // Llave secreta
            SecretKeySpec secretKeySpec = new SecretKeySpec(KEY_SECRET.getBytes(), ALGORITMO);

            // Parametros
            IvParameterSpec ivParameterSpec = new IvParameterSpec(INICIALIZACION.getBytes());


            byte[] encrypted = Base64.getDecoder().decode(textEncrypt);
            // Cifrar constraseña
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {

            throw new EncryptedException("Error al descriptar contraseña certificado");
        }

    }
}
