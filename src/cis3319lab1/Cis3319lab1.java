package cis3319lab1;

import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author tug01495
 */
public class Cis3319lab1 {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        //agent for decrypting or encrypting
        Cipher cipher;
        cipher = Cipher.getInstance("DES");
        //encrypt(cipher);
        decrypt(cipher);
        
        //cipher.init(Cipher.DECRYPT_MODE, secretKey);
        //byte[] textDecrypted = cipher.doFinal(textEncrypted);
        
    }
    
    public static void encrypt(Cipher cipher) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException{
        try{
           //create key
           SecretKey key = KeyGenerator.getInstance("DES").generateKey();
           
           //output path for secret key
           Path p = Paths.get("C:/Users/tug01495/Desktop/secretKey.txt");
           //output the secret key to selected location
           try(OutputStream out = new BufferedOutputStream(
               Files.newOutputStream(p, CREATE))){ //overwrite each time
               out.write(key.getEncoded()); //the whole thing
           }catch (IOException e){
               System.err.println(e);
           }
           //initialize cipher to ENCRYPT MODE
           cipher.init(Cipher.ENCRYPT_MODE, key);
           
           //asking user for plaintext
           Scanner sc = new Scanner(System.in);
           System.out.println("Enter plaintext: ");
           String plaintext = sc.nextLine(); //ask user for plaintext
           
           //CIPHER TEXT CREATION
           byte[] encryptedMessage = cipher.doFinal(plaintext.getBytes());
           //output path for ciphertext
           Path c = Paths.get("C:/Users/tug01495/Desktop/cipherText.txt");
           //output the ciphertext to selected location
           try(OutputStream out = new BufferedOutputStream(
               Files.newOutputStream(c, CREATE))){
               out.write(encryptedMessage);
           }catch (IOException e){
               System.err.println(e);
           }
        }
        catch(InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException e){
            System.err.println(e);
        }
    }
    
    public static void decrypt(Cipher cipher) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        try{
           //get secret key from file
           byte[] encodedKey = Files.readAllBytes(Paths.get("C:/Users/tug01495/Desktop/secretKey.txt"));
           SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "DES");
           
           cipher.init(Cipher.DECRYPT_MODE, key); 
           byte[] ciphertext = Files.readAllBytes(Paths.get("C:/Users/tug01495/Desktop/cipherText.txt"));
           byte[] plaintext = cipher.doFinal(ciphertext);
           String decryptedMessage = new String(plaintext);
            System.out.println(decryptedMessage);
        }catch(IOException | InvalidKeyException e){
            System.err.println(e);
        }
        
    }
}
