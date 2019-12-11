package com.vgipl.server.apmc.utils;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;




//import sun.misc.*;

public class AES 
{
private static String algorithm = "AES/CBC/PKCS5Padding";


        // Performs Encryption
        public static String encrypt(String plainText) throws Exception 
        {
              //  Key key = generateKey();
                Cipher chiper = Cipher.getInstance(algorithm);
                SecretKeySpec key = new SecretKeySpec("hcxilkqbbhczfeultgbskdmaunivmfuo".getBytes("ASCII"), "AES");
                IvParameterSpec iv = new IvParameterSpec("ryojvlzmdalyglrj".getBytes("ASCII"));
                chiper.init(Cipher.ENCRYPT_MODE, key,iv);
                byte[] encVal = chiper.doFinal(plainText.getBytes());
                String encryptedValue = new Base64EncoderDecoder().encode(encVal);
                return encryptedValue;
        }

        // Performs decryption
        public static String decrypt(String encryptedText) throws Exception 
        {
                // generate key 
               // Key key = generateKey();
        	SecretKeySpec key = new SecretKeySpec("hcxilkqbbhczfeultgbskdmaunivmfuo".getBytes("ASCII"), "AES");
                Cipher chiper = Cipher.getInstance(algorithm);
                IvParameterSpec iv = new IvParameterSpec("ryojvlzmdalyglrj".getBytes("ASCII"));
                chiper.init(Cipher.DECRYPT_MODE, key,iv);
                byte[] decordedValue = new Base64EncoderDecoder().decode(encryptedText);
                byte[] decValue = chiper.doFinal(decordedValue);
                String decryptedValue = new String(decValue);
                return decryptedValue;
        }


        // performs encryption & decryption 
        public static void main(String[] args) throws Exception 
        {
        		//7/O29cxO9U4smhskyBgBW/w==
                String plainText = "1111111c";
                String encryptedText = AES.encrypt(plainText);
                String decryptedText = AES.decrypt(encryptedText);

                System.out.println("Plain Text : " + plainText);
                System.out.println("Encrypted Text : " + encryptedText);
                System.out.println("Decrypted Text : " + decryptedText);
        }
        
        public static ByteArrayOutputStream getByteArray(String data) {

        	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        	DataOutputStream out = new DataOutputStream(byteArrayOutputStream);
        		
        	try {
        		out.write(data.getBytes());
        		byteArrayOutputStream.flush();
        		byteArrayOutputStream.close();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}

        	return byteArrayOutputStream;

        }
        
        public static String bytesToHex(byte[] bytes) {
            StringBuilder builder = new StringBuilder();
            for (byte b: bytes) {
              builder.append(String.format("%02x", b));
            }
            return builder.toString();
          }
}