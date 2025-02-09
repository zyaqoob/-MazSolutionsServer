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
import java.security.spec.KeySpec;
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
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *  Class that manages password encryption and decryption.
 * @author Miguel Sanchez, Zeeshan Yaqoob, Aitor Ruiz de Gauna
 */
public class Crypto {

    /**
     *
     * @param mensaje mensaje
     * @return hexadecimal
     */
    public static String cifrar(String mensaje) {
        PublicKey publicKey;
        String path=Crypto.class.getResource("PublicKey.txt").getPath();
        KeyFactory keyFactory;       
        byte[]key=fileReader(path);
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
        //We use the method covertStringToHex to get the hex value of the String
        return hexadecimal(encodedMessage);
    }

    /**
     *
     * @param password password
     * @return decifered password
     */
    public static String descifrar(String password) {
        //password=new String(hexStringToByteArray(password));
        KeyFactory factoriaRSA;
        String path=Crypto.class.getResource("PrivateKey.txt").getPath();
        PrivateKey privateKey;
        Cipher cipher;
        String desc = null;
        byte[]key = fileReader(path);
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(key);
            factoriaRSA = KeyFactory.getInstance("RSA");
            privateKey = factoriaRSA.generatePrivate(spec);
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave privada
            //cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            //IvParameterSpec ivParam = new IvParameterSpec(new byte[16]);	
            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave privada y el ivParam
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            //Le decimos que descifre
            byte[] decodedMessage = cipher.doFinal(hexStringToByteArray(password));
            // Texto descifrado
            desc = new String(decodedMessage);
            //System.out.println(desc);
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
    
    /**
     *
     * @param password password
     * @return hashedPassword
     */
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
    
    private static String convertStringToHex(String password) {
        StringBuilder hex = new StringBuilder();
        for(int i=0; i<password.length(); i++){
            int decimal=(int)i;
            hex.append(Integer.toHexString(decimal));
        }
        return hex.toString();
    }
    
    static String hexadecimal(byte[] resumen) {
        String hex = "";
        for (int i = 0; i < resumen.length; i++) {
            String h = Integer.toHexString(resumen[i] & 0xFF);
            if (h.length() == 1)
                    hex += "0";
            hex += h;
        }
        return hex.toUpperCase();
    }

    /**
     *
     * @param password password
     * @return bytes of message
     */
    public static byte[] hexStringToByteArray(String password) {
        int len = password.length();
        byte[] mensajeByte = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            mensajeByte[i / 2] = (byte) ((Character.digit(password.charAt(i), 16) << 4)
                    + Character.digit(password.charAt(i + 1), 16));
        }
        return mensajeByte;
    }
    /**
     * Descifra un texto con AES, modo CBC y padding PKCS5Padding (simétrica) y
     * lo retorna
     *
     * @param clave La clave del usuario
     * @return decoded message
     */
    public static String descifrarEmail(String clave) {
        String ret = null;
        Cipher cipher;
        byte[] salt = "esta es la salt!".getBytes();
        // Fichero leído
        byte[] fileContent = fileReader(Crypto.class.getResource("CipherEmail.txt").getPath()); // Path del fichero EjemploAES.dat
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            // Obtenemos el keySpec
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); // AES-128

            // Obtenemos una instancide de SecretKeyFactory con el algoritmo "PBKDF2WithHmacSHA1"
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            
            // Generamos la clave
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();

            // Creamos un SecretKey usando la clave + salt
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Obtenemos una instancide de Cipher con el algoritmos que vamos a usar "AES/CBC/PKCS5Padding"
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            
            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave privada
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            
            // Leemos el fichero codificado 
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16));

            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave privada y el ivParam
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            
            // Le decimos que descifre
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));

            // Texto descifrado
            ret = new String(decodedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    /**
     *
     * @param clave clave
     * @return dicifered
     */
    public static String descifrarPassword(String clave) {
        String ret = null;
        Cipher cipher;
        byte[] salt = "esta es la salt!".getBytes();
        // Fichero leído
        byte[] fileContent = fileReader(Crypto.class.getResource("PasswordEmail.txt").getPath()); // Path del fichero EjemploAES.dat
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            // Obtenemos el keySpec
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); // AES-128

            // Obtenemos una instancide de SecretKeyFactory con el algoritmo "PBKDF2WithHmacSHA1"
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            
            // Generamos la clave
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();

            // Creamos un SecretKey usando la clave + salt
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Obtenemos una instancide de Cipher con el algoritmos que vamos a usar "AES/CBC/PKCS5Padding"
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            
            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave privada
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            
            // Leemos el fichero codificado 
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16));

            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave privada y el ivParam
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            
            // Le decimos que descifre
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));

            // Texto descifrado
            ret = new String(decodedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
