/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Miguel Sanchez, Zeeshan Yaqoob, Aitor Ruiz de Gauna
 */
public class Crypto {

    public static String cifrar(String mensaje) {
        PublicKey publicKey;
        KeyFactory keyFactory;       
        byte[]key=fileReader("C:\\Users\\Aitor\\Documents\\NetBeansProjects\\MazSolutionsServer\\PublicKey.txt");
        byte[] encodedMessage = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            Cipher cipherRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            X509EncodedKeySpec spec = new X509EncodedKeySpec(key);
            publicKey = keyFactory.generatePublic(spec);
            cipherRSA.init(Cipher.ENCRYPT_MODE, publicKey);
            encodedMessage = cipherRSA.doFinal(mensaje.getBytes());
            
            //fileWriter("mensajecifrado.txt", encodedMessage);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String(encodedMessage);
    }

    public static String descifrar(String password) {
        KeyFactory factoriaRSA;
        PrivateKey privateKey;
        Cipher cipher;
        String desc = null;
        byte[]key = fileReader("C:\\Users\\Aitor\\Documents\\NetBeansProjects\\MazSolutionsServer\\PrivateKey.txt");
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(key);
            factoriaRSA = KeyFactory.getInstance("RSA");
            privateKey = factoriaRSA.generatePrivate(spec);
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave privada
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            //IvParameterSpec ivParam = new IvParameterSpec(new byte[16]);	
            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave privada y el ivParam
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            //Le decimos que descifre
            byte[] decodedMessage = cipher.doFinal(password.getBytes());
            // Texto descifrado
            desc = new String(decodedMessage);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return desc;
    }


    /**
     * Retorna el contenido de un fichero
     *
     * @param path Path del fichero
     * @return El texto del fichero
     */
    public static byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    public static String hashPassword(String password){
        MessageDigest messageDigest;
        String base64=null;
        try {
            messageDigest=MessageDigest.getInstance("SHA");
            messageDigest.update(password.getBytes("UTF-8"));
            password=new String(messageDigest.digest());
            base64=Base64.getEncoder().encodeToString(password.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }

}
