package com.cthulhu.web_service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
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
	public static String encrypt(String data, PublicKey publicKey) {
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
				| InvalidKeyException
				| NullPointerException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Function which takes the private key and decrypt the message
	 * @param data: the message which will be sent by the other web server
	 * @param privateKey: the private key which will be used to decrypt the message
	 * @return the decrypted message
	 */
	public static String decrypt(String data, PrivateKey privateKey) {
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
				| InvalidKeyException
				| NullPointerException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Function to sign a message before sending it
	 * @param data: the message which will be sent by the other web server
	 * @param privateKey: the private key which will be used to sign the message
	 * @return the signaature for the current message
	 */
	public static String sign(String data, PrivateKey privateKey) {
	    Signature signatory;
	    try {
			signatory = Signature.getInstance("SHA256withRSA");
		    signatory.initSign(privateKey);
		    signatory.update(data.getBytes());
		    return Base64.getEncoder().encodeToString(signatory.sign());
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | NullPointerException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * Function to verify the message sent by the other web service
	 * @param data: the message sent by the other web server
	 * @param signature: the signature sent by the other web server
	 * @param publicKey: the public key which will be used to verify the message
	 * @return a boolean to said if the message has no changes
	 */
	public static boolean verify(String data, String signature, PublicKey publicKey) {
		Signature publicSignature;
		try {
			publicSignature = Signature.getInstance("SHA256withRSA");
			publicSignature.initVerify(publicKey);
			publicSignature.update(Base64.getDecoder().decode(data));
			return publicSignature.verify(Base64.getDecoder().decode(signature));
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | NullPointerException e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}
	}
}
