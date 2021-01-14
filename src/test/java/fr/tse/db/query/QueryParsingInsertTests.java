package fr.tse.db.query;

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
class QueryParsingInsertTests {

	@Autowired
	private QueryService qs;
	private final static String ACTION = "insert";
	
	// ---------------------- [INSERT] [SINGLEQUERY] [BadQueryException]
	@Test
	// BadQueryException : Missing into.
	public void parseQuerySingleInsertSyntax1BadQueryExceptionTest() {
		String query = ACTION + " MySeries VALUES ((300000,10))";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_GENERAL;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing name of the series.
	public void parseQuerySingleInsertSyntax2BadQueryExceptionTest() {
		String query = ACTION + " INTO VALUES ((300000,10))";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing VALUES keyword.
	public void parseQuerySingleInsertSyntax3BadQueryExceptionTest() {
		String query = ACTION + " INTO MySeries ((300000,10))";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing values.
	public void parseQuerySingleInsertSyntax4BadQueryExceptionTest() {
		String query = ACTION + " INTO MySeries VALUES";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing first level brackets.
	public void parseQuerySingleInsertSyntax5BadQueryExceptionTest() {
		String query = ACTION + " INTO MySeries VALUES (300000,10)";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing first and second level brackets.
	public void parseQuerySingleInsertSyntax6BadQueryExceptionTest() {
		String query = ACTION + " INTO MySeries VALUES 300000,10";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Unbalanced right brackets.
	public void parseQuerySingleInsertSyntax7BadQueryExceptionTest() {
		String query = ACTION + "INTO MySeries VALUES ((300000,10)";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Unbalanced left brackets.
	public void parseQuerySingleInsertSyntax8BadQueryExceptionTest() {
		String query = ACTION + "INTO MySeries VALUES (300000,10))";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Wrong values separator 1 ';'.
	public void parseQuerySingleInsertSyntax9BadQueryExceptionTest() {
		String query = ACTION + "INTO MySeries VALUES ((300000;10))";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Wrong values separator 1 ' '.
	public void parseQuerySingleInsertSyntax10BadQueryExceptionTest() {
		String query = ACTION + "INTO MySeries VALUES ((300000 10))";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Wrong value type 1.
	public void parseQuerySingleInsertSyntax11BadQueryExceptionTest() {
		String query = ACTION + "INTO MySeries VALUES ((UnknownValue,10))";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_IN_TYPE;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Wrong value type 2.
	public void parseQuerySingleInsertSyntax12BadQueryExceptionTest() {
		String query = ACTION + "INTO MySeries VALUES ((300000,UnknownValue))";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_IN_TYPE;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Too few values.
	public void parseQuerySingleInsertSyntax13BadQueryExceptionTest() {
		String query = ACTION + "INTO MySeries VALUES ((300000))";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_IN_VALUES;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Too many values.
	public void parseQuerySingleInsertSyntax14BadQueryExceptionTest() {
		String query = ACTION + "INTO MySeries VALUES ((300000,10,42))";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_IN_VALUES;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	// ---------------------- [INSERT] [SINGLEQUERY] [OK]
	// TODO
}
