package fr.tse.db.query.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.tse.db.query.error.BadQueryException;
import fr.tse.db.query.service.QueryService;

@SpringBootTest
@RunWith(SpringRunner.class)
class QueryParsingDeleteTests {

	@Autowired
	private QueryService qs;
	private final static String ACTION = "delete";
	
	
	// ---------------------- [DELETE] [SINGLEQUERY] [BadQueryException]
	@Test
	// BadQueryException : Missing the name of the series.
	public void parseQuerySingleDeleteSyntax1BadQueryExceptionTest() {
		String query = ACTION + " from where TIMESTAMP == 12525";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_DELETE_IN_NAME;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing conditions after "WHERE".
	public void parseQuerySingleDeleteSyntax2BadQueryExceptionTest() {
		String query = ACTION + " from MySeries where";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		if(e.getMessage().contains(expectedMessage)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	// BadQueryException : Unknown attribute.
	public void parseQuerySingleDeleteSyntax3BadQueryExceptionTest() {
		String query = ACTION + " from MySeries where unknownattribute == 12525";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		if(e.getMessage().contains(expectedMessage)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	// BadQueryException : Unknown operator.
	public void parseQuerySingleDeleteSyntax4BadQueryExceptionTest() {
		String query = ACTION + " from MySeries where timestamp $ 12525";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		if(e.getMessage().contains(expectedMessage)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	// BadQueryException : Several operators in a row.
	public void parseQuerySingleDeleteSyntax5BadQueryExceptionTest() {
		String query = ACTION + " from MySeries where timestamp > == 12525";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		if(e.getMessage().contains(expectedMessage)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	// BadQueryException : Not a numerical value after an operator 1.
	public void parseQuerySingleDeleteSyntax6BadQueryExceptionTest() {
		String query = ACTION + " from MySeries where timestamp > NaN";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		if(e.getMessage().contains(expectedMessage)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	// BadQueryException : Not a numerical value after an operator 2.
	public void parseQuerySingleDeleteSyntax7BadQueryExceptionTest() {
		String query = ACTION + " from MySeries where timestamp > \"NAN\"";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		if(e.getMessage().contains(expectedMessage)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	// BadQueryException : Unknown boolean operator.
	public void parseQuerySingleDeleteSyntax8BadQueryExceptionTest() {
		String query = ACTION + " from MySeries where value > 15 nor timestamp <= 15";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_DELETE_GENERAL_1;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Several boolean operators in a row.
	public void parseQuerySingleDeleteSyntax9BadQueryExceptionTest() {
		String query = ACTION + " from MySeries where value > 15 and or timestamp <= 15";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_TOO_MANY;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Unknown attribute after boolean operator.
	public void parseQuerySingleDeleteSyntax10BadQueryExceptionTest() {
		String query = ACTION + " from MySeries where value > 15 and unknownattribute <= 15";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		if(e.getMessage().contains(expectedMessage)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	// BadQueryException : Unknown operator after boolean operator.
	public void parseQuerySingleDeleteSyntax11BadQueryExceptionTest() {
		String query = ACTION + " from MySeries where value > 15 and timestamp $ 15";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		if(e.getMessage().contains(expectedMessage)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	// BadQueryException : Several operators after boolean operator.
	public void parseQuerySingleDeleteSyntax12BadQueryExceptionTest() {
		String query = ACTION + " from MySeries where value > 15 and timestamp > == 15";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		if(e.getMessage().contains(expectedMessage)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	// BadQueryException : Not a numerical value after boolean operator 1.
	public void parseQuerySingleDeleteSyntax13BadQueryExceptionTest() {
		String query = ACTION + " from MySeries where value > 15 and timestamp <= Nan";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		if(e.getMessage().contains(expectedMessage)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	// BadQueryException : Not a numerical value after boolean operator 2.
	public void parseQuerySingleDeleteSyntax14BadQueryExceptionTest() {
		String query = ACTION + " from MySeries where value > 15 and timestamp <= \"Nan\"";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		if(e.getMessage().contains(expectedMessage)) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	// BadQueryException : Missing ALL or FROM.
	public void parseQuerySingleDeleteSyntax15BadQueryExceptionTest() {
		String query = ACTION;
		String expectedMessage = BadQueryException.ERROR_MESSAGE_DELETE_GENERAL_1;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : No Series name specified.
	public void parseQuerySingleDeleteSyntax16BadQueryExceptionTest() {
		String query = ACTION + " from ";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_DELETE_IN_NAME;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	// ---------------------- [DELETE] [OK]
	@Test
	// Delete a Series
	public void parseQueryDeleteExample1Test() throws BadQueryException {
		String query = ACTION + " from MySeries";
		HashMap<String, Object> expectedHashMap = new HashMap();
		expectedHashMap.put("conditions", null);
		expectedHashMap.put("series", "MySeries");
		expectedHashMap.put("action", "delete");
		
		HashMap<String, Object> returnedHashMap = qs.parseQuery(query);
		assertEquals(expectedHashMap, returnedHashMap);
	}
	
	@Test
	// Delete with condition
	public void parseQueryDeleteExample2Test() throws BadQueryException {
		String query = ACTION + " from MySeries where timestamp == 12525";
		HashMap<String, Object> expectedHashMap = new HashMap();
	    HashMap<String, Object> whereConditions = new HashMap<>();
	    whereConditions.put("operators", "==");
	    whereConditions.put("timestamps",(long) 12525);
	    whereConditions.put("join", null);
	    expectedHashMap.put("timestamps", whereConditions.get("timestamps"));
	    expectedHashMap.put("operators", whereConditions.get("operators"));
	    expectedHashMap.put("join", whereConditions.get("join"));
		expectedHashMap.put("series", "MySeries");
		expectedHashMap.put("action", "delete");
		
		HashMap<String, Object> returnedHashMap = qs.parseQuery(query);
		assertEquals(expectedHashMap, returnedHashMap);
	}
	
	@Test
	// Delete with several conditions
	public void parseQueryDeleteExample3Test() throws BadQueryException {
		String query = ACTION + " from MySeries where timestamp > 5 and timestamp < 15";
		HashMap<String, Object> expectedHashMap = new HashMap();
	    HashMap<String, Object> whereConditions = new HashMap<>();
	    List<String> operators = new ArrayList<String>();
	    operators.add(">");
	    operators.add("<");
	    List<Long> timestamps = new ArrayList<Long>();
	    timestamps.add((long)5);
	    timestamps.add((long)15);
	    whereConditions.put("operators", operators);
	    whereConditions.put("timestamps",timestamps);
	    whereConditions.put("join", "and");
	    expectedHashMap.put("timestamps", whereConditions.get("timestamps"));
	    expectedHashMap.put("operators", whereConditions.get("operators"));
	    expectedHashMap.put("join", whereConditions.get("join"));
		expectedHashMap.put("series", "MySeries");
		expectedHashMap.put("action", "delete");
		
		HashMap<String, Object> returnedHashMap = qs.parseQuery(query);
		assertEquals(expectedHashMap, returnedHashMap);
	}
	
}
