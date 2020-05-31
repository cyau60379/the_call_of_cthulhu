package com.cthulhu.web_service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.junit.jupiter.api.Test;


public class KeyLoaderTests {
	
	@Test
	void testGetPrivateKeyWithExistingKey_ShouldBeAKey() {
		PrivateKey pk = KeyLoader.getPrivateKey("src\\main\\resources\\static\\private_key_Spring.der");
		assertEquals("sun.security.rsa.RSAPrivateCrtKeyImpl@ffc73dbd", pk.toString());
	}
	
	@Test
	void testGetPrivateKeyWithNull_ShouldBeNull() {
		PrivateKey pk = KeyLoader.getPrivateKey(null);
		assertEquals(null, pk);
	}
	
	@Test
	void testGetPrivateKeyWithUnexistingKey_ShouldBeNull() {
		PrivateKey pk = KeyLoader.getPrivateKey("src\\main\\resourcdses\\stafsdtic\\privatedfsd_key_Spring.der");
		assertEquals(null, pk);
	}
	
	@Test
	void testGetPrivateKeyWithPublicKey_ShouldBeNull() {
		PrivateKey pk = KeyLoader.getPrivateKey("src\\main\\resources\\static\\public_key_Django.der");
		assertEquals(null, pk);
	}
	
	@Test
	void testGetPublicKeyWithExistingKey_ShouldBeAKey() {
		PublicKey pk = KeyLoader.getPublicKey("src\\main\\resources\\static\\public_key_Django.der");
		assertEquals("Sun RSA public key, 2048 bits\n" + 
				"  modulus: 25507096287476894045251523154910086996029882135624759108337368196686118431752865762138916733374057256389784510092794073560643182232290577320700678627922749566454042028736509207867963847944836236807707445642365966087466895207164517861553456357076739423761767692095558551543328143736710493917034098266175814544305430637032062864022167915258906555468006562910639198829956239861814607558784206360365318929589369277162687148318446681615406261647089656462833723985437547553946331148192507385449405948334744443467675129688855378306608834853578214218240873070216085375195403958917223730566686645931142458503552318837532907449\n" + 
				"  public exponent: 65537", pk.toString());
	}
	
	@Test
	void testGetPublicKeyWithNull_ShouldBeNull() {
		PublicKey pk = KeyLoader.getPublicKey(null);
		assertEquals(null, pk);
	}
	
	@Test
	void testGetPublicKeyWithUnexistingKey_ShouldBeNull() {
		PublicKey pk = KeyLoader.getPublicKey("src\\main\\resourcdses\\stafsdtic\\privatedfsd_key_Spring.der");
		assertEquals(null, pk);
	}
	
	@Test
	void testGetPublicKeyWithPrivateKey_ShouldBeNull() {
		PublicKey pk = KeyLoader.getPublicKey("src\\main\\resources\\static\\private_key_Spring.der");
		assertEquals(null, pk);
	}

}
