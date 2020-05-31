package com.cthulhu.web_service;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 
 * @author Cyril AUBOURG
 * Class which loads the private and the public key contained in the resources
 *
 */
public class KeyLoader {
	
	/**
	 * Function which initialize the key factory
	 * @param algorithm: algorithm of the key
	 * @return the key factory
	 */
	private static KeyFactory getFactory(String algorithm) {
		try {
			KeyFactory factory = KeyFactory.getInstance(algorithm);    //initialize the factory on RSA algorithm
			return factory;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No such algorithm: Verify that you use an existing algorithm");
			return null;
		}
	}
	
	/**
	 * Function which transform data in byte tab
	 * @param fileLocation: the location of the private key
	 * @return the byte table of the file's data
	 */
	private static byte[] prepareData(String fileLocation) {
		
		/******************** Variables **********************/
		File file = new File(fileLocation);   //load the file
		FileInputStream fileStream;    //input bytes from the file
		DataInputStream dataStream;    //read Java primitive data types (here input bytes from fileStream)
		byte[] byteData;    //bytes tab from the data file
		
		try {
			fileStream = new FileInputStream(file);
			dataStream = new DataInputStream(fileStream);
	        byteData = new byte[(int)file.length()];
	        try {    //fill with the bytes from the fileStream
				dataStream.read(byteData);
				dataStream.close();
				return byteData;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found, PLEASE verify that the location " + fileLocation + " exists.");
			return null;
		}
	}
	
	/**
	 * Function which takes the private key of this server and convert it for Java use
	 * @param fileLocation: the location of the private key
	 * @return the private key
	 */
	public static PrivateKey getPrivateKey(String fileLocation) {
		
		/******************** Variables **********************/
		byte[] privateKeyB;    //bytes tab from the private key
		KeyFactory factory;    //factory which will generate the key from the decoded file
		PKCS8EncodedKeySpec privateSpecification;   //specification on the encoded method of the input file
		PrivateKey privateKey;    //the private key which will be returned
		
		factory = KeyLoader.getFactory("RSA");
		privateKeyB = KeyLoader.prepareData(fileLocation);
		if(privateKeyB == null) {
			return null;
		}
        privateSpecification = new PKCS8EncodedKeySpec(privateKeyB);  //initialize the specification of the key (PKCS8 here)
        
		try {
			privateKey = factory.generatePrivate(privateSpecification);  //generate the new key
		} catch (InvalidKeySpecException e) {
			System.out.println("Unvailable Key Spec: Verify that you use the right key");
			privateKey = null;
		}
		return privateKey;   //return the private key
	}
	
	/**
	 * Function which takes the public key of the other server and convert it for Java use
	 * @param fileLocation: the location of the public key
	 * @return the public key
	 */
	public static PublicKey getPublicKey(String fileLocation) {
		
		/******************** Variables **********************/
		byte[] publicKeyB;    //bytes tab from the public key
		KeyFactory factory;    //factory which will generate the key from the decoded file
		X509EncodedKeySpec publicSpecification;   //specification on the encoded method of the input file
		PublicKey publicKey;    //the public key which will be returned
		
		factory = KeyLoader.getFactory("RSA");
		publicKeyB = KeyLoader.prepareData(fileLocation);
		if(publicKeyB == null) {
			return null;
		}
        publicSpecification = new X509EncodedKeySpec(publicKeyB);  //initialize the specification of the key (PKCS8 here)
        
		try {
			publicKey = factory.generatePublic(publicSpecification);  //generate the new key
		} catch (InvalidKeySpecException e) {
			System.out.println("Unvailable Key Spec: Verify that you use the right key");
			publicKey = null;
		}
		return publicKey;   //return the public key
	}
}
