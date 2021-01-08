package fr.tse.db.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.tse.db.query.error.BadQueryException;
import fr.tse.db.query.service.QueryService;

class QueryParsingInsertTests {

	@Autowired
	private QueryService qs;
	private final static String ACTION = "INSERT";
	
	// ---------------------- [INSERT] [SINGLEQUERY] [BadQueryException]
	@Test
	// BadQueryException : Missing ';' at the end of the query.
	public void parseQuerySingleInsertSyntax1BadQueryExceptionTest() {
		String query = ACTION + " INTO MySeries VALUES ((300000,10))";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing into.
	public void parseQuerySingleInsertSyntax2BadQueryExceptionTest() {
		String query = ACTION + " MySeries VALUES ((300000,10));";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing name of the series.
	public void parseQuerySingleInsertSyntax3BadQueryExceptionTest() {
		String query = ACTION + " INTO VALUES ((300000,10));";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing VALUES keyword.
	public void parseQuerySingleInsertSyntax4BadQueryExceptionTest() {
		String query = ACTION + " INTO MySeries ((300000,10));";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing values.
	public void parseQuerySingleInsertSyntax5BadQueryExceptionTest() {
		String query = ACTION + " INTO MySeries VALUES;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing first level brackets.
	public void parseQuerySingleInsertSyntax6BadQueryExceptionTest() {
		String query = ACTION + " INTO MySeries VALUES (300000,10);";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing first and second level brackets.
	public void parseQuerySingleInsertSyntax7BadQueryExceptionTest() {
		String query = ACTION + " INTO MySeries VALUES 300000,10;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Unbalanced right brackets.
	public void parseQuerySingleInsertSyntax8BadQueryExceptionTest() {
		String query = ACTION + "INTO MySeries VALUES ((300000,10);";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Unbalanced left brackets.
	public void parseQuerySingleInsertSyntax9BadQueryExceptionTest() {
		String query = ACTION + "INTO MySeries VALUES (300000,10));";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Wrong values separator 1 ';'.
	public void parseQuerySingleInsertSyntax10BadQueryExceptionTest() {
		String query = ACTION + "INTO MySeries VALUES ((300000;10));";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Wrong values separator 1 ' '.
	public void parseQuerySingleInsertSyntax11BadQueryExceptionTest() {
		String query = ACTION + "INTO MySeries VALUES ((300000 10));";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Disorder.
	public void parseQuerySingleInsertSyntax12BadQueryExceptionTest() {
		String query = "INTO MySeries " + ACTION + " VALUES ((300000,10));";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	// ---------------------- [INSERT] [SINGLEQUERY] [OK]
	// TODO
}
