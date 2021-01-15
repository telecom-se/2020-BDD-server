package fr.tse.db.query.service;

import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.tse.db.query.error.BadQueryException;
import fr.tse.db.query.service.QueryService;

public class QueryParsingShowTests {
	@Autowired
	private QueryService queryService;
	
	@Test
	// BadQueryException : Test when the Show Query is correct
	public void parseQuerySelectSyntaxQueryExceptionTest() throws BadQueryException {
	    String queryTest = "show";
	    HashMap<String, Object> expectedSeries = new HashMap<String, Object>();
	    expectedSeries.put("action", "show");
	    Assertions.assertEquals(expectedSeries,queryService.parseQuery(queryTest));
	}
	
	@Test
	// BadQueryException : Test when the Show Query is not specified
	public void parseQuerySelectUnspecifiedBadQueryExceptionTest() {
	    String queryTest = "myseries;";
	    String expectedMessage = "Error in SHOW query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Show Query is not correct
	public void parseQuerySelectSyntaxBadQueryExceptionTest() {
	    String queryTest = "showed;";
	    String expectedMessage = "Error in SHOW query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Series in Query is not specified
	public void parseQuerySelectSeriesBadQueryExceptionTest() {
	    String queryTest = "show from;";
	    String expectedMessage = "Error in SHOW query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}	
	
	@Test
	// BadQueryException : Test when the From in Query is incorrect
	public void parseQuerySelectFromSyntaxBadQueryExceptionTest() {
	    String queryTest = "show mySeries rom;";
	    String expectedMessage = "Error in SHOW query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Conditions in Query are not specified
	public void parseQuerySelectConditionsUnspecifiedBadQueryExceptionTest() {
	    String queryTest = "show all from myseries where;";
	    String expectedMessage = "Error in SHOW query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    // Pb : queryService.parseQuery(quertyTest) ne renvoie pas d'exception
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Conditions Syntax are incorrect
	public void parseQuerySelectConditionsSyntaxBadQueryExceptionTest() {
	    String queryTest = "show all from myseries were timestamp == 15;";
	    String expectedMessage = "Error in SHOW query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    // Pb : queryService.parseQuery(quertyTest) renvoie un nullPointerException 
	    // Ligne 56 QueryService.java
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	@Test
	// BadQueryException : Test when the Conditions Syntax are incorrect
	public void parseQuerySelectConditionsSyntaxBracketBadQueryExceptionTest() {
	    String queryTest = "show all from myseries were timestamp == (15);";
	    String expectedMessage = "Error in SHOW query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    // Pb : queryService.parseQuery(quertyTest) renvoie un nullPointerException 
	    // Ligne 56 QueryService.java
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Query is correct
	public void parseQuerySelectTest() throws BadQueryException {
	    String queryTest = "show all from myseries where timestamp == 15;";
	    @SuppressWarnings("unused")
		HashMap<String, Object> expectedMap = new HashMap<String, Object>();
	    expectedMap = queryService.parseQuery(queryTest);
	}
}
