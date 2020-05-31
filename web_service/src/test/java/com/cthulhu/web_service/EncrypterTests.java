package com.cthulhu.web_service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.security.PrivateKey;
import java.security.PublicKey;


public class EncrypterTests {

	@Test
	void testEncryptionMethod() {
		PublicKey pk = KeyLoader.getPublicKey("src\\main\\resources\\static\\public_key_Django.der");
		String message = "Hello world";
		String encryptedMessage = Encrypter.encrypt(message, pk);
		assertTrue(encryptedMessage != null);
	}
	
	@Test
	void testEncryptionMethodWithNullMessage_ShouldBeNull() {
		PublicKey pk = KeyLoader.getPublicKey("src\\main\\resources\\static\\public_key_Django.der");
		String message = null;
		String encryptedMessage = Encrypter.encrypt(message, pk);
		assertEquals(null, encryptedMessage);
	}
	
	@Test
	void testEncryptionMethodWithWrongKey_ShouldBeNull() {
		PublicKey pk = KeyLoader.getPublicKey("src\\main\\resourcs\\stic\\puc_key_Django.der");
		String message = "Hello world";
		String encryptedMessage = Encrypter.encrypt(message, pk);
		assertEquals(null, encryptedMessage);
	}

	@Test
	void testEncryptionMethodWithLongString_ShouldBeNull() {
		PublicKey pk = KeyLoader.getPublicKey("src\\main\\resources\\static\\public_key_Django.der");
		String message = "Helloworldfezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
		String encryptedMessage = Encrypter.encrypt(message, pk);
		assertEquals(null, encryptedMessage);
	}
	
	@Test
	void testDecryptionMethod() {
		PrivateKey pk = KeyLoader.getPrivateKey("src\\main\\resources\\static\\private_key_Spring.der");
		String message = "C5g2los3ZYTdrtTyybPBQpaeevoo0sm5Q8NnDTE+NKO0T9sHSsQBEr5nMC4zmEMlob/+CNpqtVuxC/UBJ6d6gRlNsUJlXd2IGUH+VF9INBjA1IeGxXaq47zdRD50YXsIWXtY7KQxcyijK4i9a5aj2B8Dm1hZ+qizeY9pET4MRJjtiXHw0dOVcZBlGEp4eaMRndCbks6UxuwViOm2rtmPtOBkLyObzjZLnHmVHzSHpd8o8Mh+7gdNot9DRsi/sJWdAufMt2uD3HnuDUCyzVDzJsxZNze+EtJZn0v6lZg8ASoMPHQz/MyE9caeloWOrDe422iny73ccec0mTWFcCx+OA==";
		String decryptedMessage = Encrypter.decrypt(message, pk);
		assertTrue(decryptedMessage != null);
	}
	
	@Test
	void testDecryptionMethodWithNullMessage_ShouldBeNull() {
		PrivateKey pk = KeyLoader.getPrivateKey("src\\main\\resources\\static\\private_key_Spring.der");
		String message = null;
		String decryptedMessage = Encrypter.decrypt(message, pk);
		assertEquals(null, decryptedMessage);
	}
	
	@Test
	void testDecryptionMethodWithWrongKey_ShouldBeNull() {
		PrivateKey pk = KeyLoader.getPrivateKey("src\\main\\resourcs\\stic\\puc_key_Django.der");
		String message = "Hello world";
		String decryptedMessage = Encrypter.decrypt(message, pk);
		assertEquals(null, decryptedMessage);
	}

	@Test
	void testDecryptionMethodWithLongString_ShouldBeNull() {
		PrivateKey pk = KeyLoader.getPrivateKey("src\\main\\resources\\static\\private_key_Spring.der");
		String message = "Helloworldfezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
		String decryptedMessage = Encrypter.decrypt(message, pk);
		assertEquals(null, decryptedMessage);
	}
	
	@Test
	void testSignatureMethod() {
		PrivateKey pk = KeyLoader.getPrivateKey("src\\main\\resources\\static\\private_key_Spring.der");
		String message = "Hello world";
		String signature = Encrypter.sign(message, pk);
		assertTrue(signature != null);
	}
	
	@Test
	void testSignatureMethodWithNullMessage_ShouldBeNull() {
		PrivateKey pk = KeyLoader.getPrivateKey("src\\main\\resources\\static\\private_key_Spring.der");
		String message = null;
		String signature = Encrypter.sign(message, pk);
		assertEquals(null, signature);
	}
	
	@Test
	void testSignatureMethodWithWrongKey_ShouldBeNull() {
		PrivateKey pk = KeyLoader.getPrivateKey("src\\main\\resourcs\\stic\\puc_key_Django.der");
		String message = "Hello world";
		String signature = Encrypter.sign(message, pk);
		assertEquals(null, signature);
	}
	
	@Test
	void testVerificationMethod() {
		PublicKey pk = KeyLoader.getPublicKey("src\\main\\resources\\static\\public_key_Django.der");
		String message = "hShrgb0eto5d3oIYi0bMuBWAhKKe2U35gi49vUhx2o74castyvk7jcjVg6SL2KMuUrlhOiqAeazqTkLWsx2pTeLaz/UcMWwiC6yIGAThbb+Rg1+BnHm0aHQFq1OCUjntd1b/rwWa0JxyT0TdukAFNwG73tLCiJtOv7zmcRtyz59QzvBXz8uJdcvkT9dQj8gZj99sFJuASKBQ2YY1+n4iC2PuYfmxFuO0RLYBLlaoFF/4xAB1eYdB/gQ9ICUWgdJ0WS3Px28uNfl014T+t/WWp92Egs3lgDE4F4NhOxjYWodFdp04J0r0k4vl9fsPl4ssKp0et7GGdkusfjwL3whO1g==";
		String signature = "Ft4hq1ub86WMZFcB/+SY4RDst2TtU2rwDEs8HjIZWmnILy4mPo4Aou6+P83maIuwXMLl3+unGvYALFA6mn/rVC27PePNr7+G9smHKpHB9OlAXeZ2V0FEiq2JMGCCyTQvYNy5H6wc46grGJ3wctVsDisS+yYEZBAXFjHpy9fFh3fBRpLM3OD4JRvHpIO27rJjGnLxzyYQAiPso3kGGBKXDyb3RzfTsUOYe8N3rt8u3VSqCbvpLN31JNaepmLQ2YEjpYan6VBpbK64a6pIWMLXgw1WpUqWngY955EkEebYqIWTp4FEO4aQUWISrbh74rlfzMWpfRSjWMjbqQ52sKBbnQ==";
		boolean isVerified = Encrypter.verify(message, signature, pk);
		assertTrue(isVerified);
	}
	
	@Test
	void testVerificationMethodWithNullMessage_ShouldBeFalse() {
		PublicKey pk = KeyLoader.getPublicKey("src\\main\\resources\\static\\public_key_Django.der");
		String message = null;
		String signature = "Ft4hq1ub86WMZFcB/+SY4RDst2TtU2rwDEs8HjIZWmnILy4mPo4Aou6+P83maIuwXMLl3+unGvYALFA6mn/rVC27PePNr7+G9smHKpHB9OlAXeZ2V0FEiq2JMGCCyTQvYNy5H6wc46grGJ3wctVsDisS+yYEZBAXFjHpy9fFh3fBRpLM3OD4JRvHpIO27rJjGnLxzyYQAiPso3kGGBKXDyb3RzfTsUOYe8N3rt8u3VSqCbvpLN31JNaepmLQ2YEjpYan6VBpbK64a6pIWMLXgw1WpUqWngY955EkEebYqIWTp4FEO4aQUWISrbh74rlfzMWpfRSjWMjbqQ52sKBbnQ==";
		boolean isVerified = Encrypter.verify(message, signature, pk);
		assertFalse(isVerified);
	}
	
	@Test
	void testVerificationMethodWithNullSignature_ShouldBeFalse() {
		PublicKey pk = KeyLoader.getPublicKey("src\\main\\resources\\static\\public_key_Django.der");
		String message = "hShrgb0eto5d3oIYi0bMuBWAhKKe2U35gi49vUhx2o74castyvk7jcjVg6SL2KMuUrlhOiqAeazqTkLWsx2pTeLaz/UcMWwiC6yIGAThbb+Rg1+BnHm0aHQFq1OCUjntd1b/rwWa0JxyT0TdukAFNwG73tLCiJtOv7zmcRtyz59QzvBXz8uJdcvkT9dQj8gZj99sFJuASKBQ2YY1+n4iC2PuYfmxFuO0RLYBLlaoFF/4xAB1eYdB/gQ9ICUWgdJ0WS3Px28uNfl014T+t/WWp92Egs3lgDE4F4NhOxjYWodFdp04J0r0k4vl9fsPl4ssKp0et7GGdkusfjwL3whO1g==";
		String signature = null;
		boolean isVerified = Encrypter.verify(message, signature, pk);
		assertFalse(isVerified);
	}
	
	@Test
	void testVerificationMethodWithWrongSignature_ShoudBeFalse() {
		PublicKey pk = KeyLoader.getPublicKey("src\\main\\resources\\static\\public_key_Django.der");
		String message = "C5g2los3ZYTdrtTyybPBQpaeevoo0sm5Q8NnDTE+NKO0T9sHSsQBEr5nMC4zmEMlob/+CNpqtVuxC/UBJ6d6gRlNsUJlXd2IGUH+VF9INBjA1IeGxXaq47zdRD50YXsIWXtY7KQxcyijK4i9a5aj2B8Dm1hZ+qizeY9pET4MRJjtiXHw0dOVcZBlGEp4eaMRndCbks6UxuwViOm2rtmPtOBkLyObzjZLnHmVHzSHpd8o8Mh+7gdNot9DRsi/sJWdAufMt2uD3HnuDUCyzVDzJsxZNze+EtJZn0v6lZg8ASoMPHQz/MyE9caeloWOrDe422iny73ccec0mTWFcCx+OA==";
		String signature = "dfsdfs";
		boolean isVerified = Encrypter.verify(message, signature, pk);
		assertFalse(isVerified);
	}
	
	@Test
	void testVerificationMethodWithWrongKey_ShouldBeNull() {
		PublicKey pk = KeyLoader.getPublicKey("src\\main\\resourcs\\stic\\puc_key_Django.der");
		String message = "hShrgb0eto5d3oIYi0bMuBWAhKKe2U35gi49vUhx2o74castyvk7jcjVg6SL2KMuUrlhOiqAeazqTkLWsx2pTeLaz/UcMWwiC6yIGAThbb+Rg1+BnHm0aHQFq1OCUjntd1b/rwWa0JxyT0TdukAFNwG73tLCiJtOv7zmcRtyz59QzvBXz8uJdcvkT9dQj8gZj99sFJuASKBQ2YY1+n4iC2PuYfmxFuO0RLYBLlaoFF/4xAB1eYdB/gQ9ICUWgdJ0WS3Px28uNfl014T+t/WWp92Egs3lgDE4F4NhOxjYWodFdp04J0r0k4vl9fsPl4ssKp0et7GGdkusfjwL3whO1g==";
		String signature = "Ft4hq1ub86WMZFcB/+SY4RDst2TtU2rwDEs8HjIZWmnILy4mPo4Aou6+P83maIuwXMLl3+unGvYALFA6mn/rVC27PePNr7+G9smHKpHB9OlAXeZ2V0FEiq2JMGCCyTQvYNy5H6wc46grGJ3wctVsDisS+yYEZBAXFjHpy9fFh3fBRpLM3OD4JRvHpIO27rJjGnLxzyYQAiPso3kGGBKXDyb3RzfTsUOYe8N3rt8u3VSqCbvpLN31JNaepmLQ2YEjpYan6VBpbK64a6pIWMLXgw1WpUqWngY955EkEebYqIWTp4FEO4aQUWISrbh74rlfzMWpfRSjWMjbqQ52sKBbnQ==";
		boolean isVerified = Encrypter.verify(message, signature, pk);
		assertFalse(isVerified);
	}
	
}
