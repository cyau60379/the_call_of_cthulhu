package com.cthulhu.web_service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class WebServiceApplicationTests {

	@InjectMocks
	WebServiceApplication controller;
	

	@Test
	void testCreatureSearchWithCreature() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        ResponseEntity<String> responseEntity = controller.creatureSearch("{\"name\":\"Cthulhu\", \"searchType\":\"creature\"}");
         
        assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testCreatureSearchWithEmptyString() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        ResponseEntity<String> responseEntity = controller.creatureSearch("");
         
        assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testCreatureSearchWithNull() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        ResponseEntity<String> responseEntity = controller.creatureSearch(null);
         
        assertEquals(503, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testAuthorSearchWithSurname() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        ResponseEntity<String> responseEntity = controller.authorSearch("{\"name\":\"Lovecraft\", \"searchType\":\"surname\"}");
         
        assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testAuthorSearchWithEmptyString() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        ResponseEntity<String> responseEntity = controller.authorSearch("");
         
        assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testAuthorSearchWithNull() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        ResponseEntity<String> responseEntity = controller.authorSearch(null);
         
        assertEquals(503, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testBookSearchWithCreature() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        ResponseEntity<String> responseEntity = controller.bookSearch("{\"name\":\"The Call of Cthulhu\", \"searchType\":\"book\"}");
         
        assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testBookSearchWithEmptyString() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        ResponseEntity<String> responseEntity = controller.bookSearch("");
         
        assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testBookSearchWithNull() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        ResponseEntity<String> responseEntity = controller.bookSearch(null);
         
        assertEquals(503, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testAffiliationSearchWithCreature() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        ResponseEntity<String> responseEntity = controller.affiliationSearch("{\"name\":\"Great Old Ones\", \"searchType\":\"name\"}");
         
        assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testAffiliationSearchWithEmptyString() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        ResponseEntity<String> responseEntity = controller.affiliationSearch("");
         
        assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testAffiliationSearchWithNull() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        ResponseEntity<String> responseEntity = controller.affiliationSearch(null);
         
        assertEquals(503, responseEntity.getStatusCodeValue());
	}

}
