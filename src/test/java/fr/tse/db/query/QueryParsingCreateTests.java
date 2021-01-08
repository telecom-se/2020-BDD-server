package fr.tse.db.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.tse.db.query.error.BadQueryException;
import fr.tse.db.query.service.QueryService;

class QueryParsingCreateTests {

	@Autowired
	private QueryService qs;
	private final static String ACTION = "CREATE";
	
	// ---------------------- [CREATE] [SINGLEQUERY] [BadQueryException]
	@Test
	// BadQueryException : Missing ';' at the end of the query.
	public void parseQuerySingleCreateSyntax1BadQueryExceptionTest() {
		String query = ACTION + " MySeries int64";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing name.
	public void parseQuerySingleCreateSyntax2BadQueryExceptionTest() {
		String query = ACTION + " int64;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing type.
	public void parseQuerySingleCreateSyntax3BadQueryExceptionTest() {
		String query = ACTION + " MySeries;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing name and type.
	public void parseQuerySingleCreateSyntax4BadQueryExceptionTest() {
		String query = ACTION + ";";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Name and type inverted.
	public void parseQuerySingleCreateSyntax5BadQueryExceptionTest() {
		String query = ACTION + " int64 MySeries;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : unknown type.
	public void parseQuerySingleCreateSyntax6BadQueryExceptionTest() {
		String query = ACTION + " MySeries uintlong42;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : disordered.
	public void parseQuerySingleCreateSyntax7BadQueryExceptionTest() {
		String query = "MySeries " + ACTION + " uintlong42;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	// ---------------------- [CREATE] [SINGLEQUERY] [OK]
	// TODO
}
