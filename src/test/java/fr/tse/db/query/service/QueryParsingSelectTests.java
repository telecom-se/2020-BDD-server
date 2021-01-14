package fr.tse.db.query.service;

import java.util.HashMap;
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
public class QueryParsingSelectTests {
	
	@Autowired
	private QueryService queryService;
	
	@Test
	// BadQueryException : Test when the Select Query is correct
	public void parseQuerySelectSyntaxQueryExceptionTest() throws BadQueryException {
	    String queryTest = "SELECT";
	    HashMap<String, Object> expectedSeries = new HashMap<String, Object>();
        HashMap<String, Object> test = queryService.parseQuery(queryTest);
        System.out.println(test+"test");
	    Assertions.assertEquals(expectedSeries,test);
	}
	
	@Test
	// BadQueryException : Test when the Select Query is not specified
	public void parseQuerySelectUnspecifiedBadQueryExceptionTest() {
	    String queryTest = "mySeries;";
	    String expectedMessage = "Bad action provided";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Select Query is not correct
	public void parseQuerySelectSyntaxBadQueryExceptionTest() {
	    String queryTest = "SELECTED;";
	    String expectedMessage = "Error in SELECT query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Series in Query is not specified
	public void parseQuerySelectSeriesBadQueryExceptionTest() {
	    String queryTest = "SELECT FROM;";
	    String expectedMessage = "Error in SELECT query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}	
	
	@Test
	// BadQueryException : Test when the From in Query is incorrect
	public void parseQuerySelectFromSyntaxBadQueryExceptionTest() {
	    String queryTest = "SELECT mySeries ROM;";
	    String expectedMessage = "Error in SELECT query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Conditions in Query are not specified
	public void parseQuerySelectConditionsUnspecifiedBadQueryExceptionTest() {
	    String queryTest = "SELECT ALL FROM MySeries WHERE;";
	    String expectedMessage = "Error in SELECT query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    // Pb : queryService.parseQuery(quertyTest) ne renvoie pas d'exception
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Conditions Syntax are incorrect
	public void parseQuerySelectConditionsSyntaxBadQueryExceptionTest() {
	    String queryTest = "SELECT ALL FROM MySeries WERE TIMESTAMP == 15;";
	    String expectedMessage = "Error in SELECT query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    // Pb : queryService.parseQuery(quertyTest) renvoie un nullPointerException 
	    // Ligne 56 QueryService.java
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	@Test
	// BadQueryException : Test when the Conditions Syntax are incorrect
	public void parseQuerySelectConditionsSyntaxBracketBadQueryExceptionTest() {
	    String queryTest = "SELECT ALL FROM MySeries WERE TIMESTAMP == (15);";
	    String expectedMessage = "Error in SELECT query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    // Pb : queryService.parseQuery(quertyTest) renvoie un nullPointerException 
	    // Ligne 56 QueryService.java
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Query is correct
	public void parseQuerySelectTest() throws BadQueryException {
	    String queryTest = "SELECT ALL FROM MySeries WHERE TIMESTAMP == 15;";
	    @SuppressWarnings("unused")
		HashMap<String, Object> expectedMap = new HashMap<String, Object>();
	    expectedMap = queryService.parseQuery(queryTest);
	}
	
	@Test
	public void parseQueryBadQueryExceptionBadActionProvidedTest() {
	    String query = "CREATT MySeries int64;";
	    String expectedMessage = "Bad action provided";
	        
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(query));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
}
