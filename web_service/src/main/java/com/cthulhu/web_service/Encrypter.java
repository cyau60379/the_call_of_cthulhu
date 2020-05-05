package com.cthulhu.web_service;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * 
 * @author Cyril AUBOURG
 *
 */
public class Encrypter {
	/**
	 * Function which takes the public key and encrypt the message
	 * @param data: the message which will be sent to the other web server
	 * @param publicKey: the public key which will be used to encrypt the message
	 * @return the encrypted message
	 */
	public static String encrypt(String data, Key publicKey) {
		Cipher encoder;
		try {
			encoder = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			encoder.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] encryptedData = encoder.doFinal(data.getBytes());
			String stringEncryptedData = Base64.getEncoder().encodeToString(encryptedData);
            return stringEncryptedData;
		} catch (NoSuchAlgorithmException 
				| NoSuchPaddingException 
				| IllegalBlockSizeException 
				| BadPaddingException 
				| InvalidKeyException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Function which takes the private key and decrypt the message
	 * @param data: the message which will be sent by the other web server
	 * @param privateKey: the private key which will be used to decrypt the message
	 * @return the decrypted message
	 */
	public static String decrypt(String data, Key privateKey) {
		Cipher decoder;
		try {
			decoder = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			decoder.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] newData = Base64.getDecoder().decode(data.getBytes());
			byte[] decryptedData = decoder.doFinal(newData);
			String stringDecryptedData = new String(decryptedData);
            return stringDecryptedData;
		} catch (NoSuchAlgorithmException 
				| NoSuchPaddingException 
				| IllegalBlockSizeException 
				| BadPaddingException 
				| InvalidKeyException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	public static void main(String[] args) {
		String data = "message";
		String encrypt = Encrypter.encrypt(data, KeyLoader.getPublicKey("src\\main\\resources\\static\\public_key_Django.der"));
		System.out.println("message: " + encrypt);
		System.out.println("message: " + Encrypter.decrypt(encrypt, KeyLoader.getPrivateKey("src\\main\\resources\\static\\privateKeySpring.der")));
	}
	*/
}
