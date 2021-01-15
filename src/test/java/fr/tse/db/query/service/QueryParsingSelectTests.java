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
import fr.tse.db.storage.data.DataBase;
import fr.tse.db.storage.data.Int32;
import fr.tse.db.storage.data.Series;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QueryParsingSelectTests {
	
	@Autowired
	private QueryService queryService;
	
	@Test
	// BadQueryException : Test when the Select Query is correct
	public void parseQuerySelectSyntaxQueryExceptionTest() throws BadQueryException {
	    String queryTest = "select all from myseries where timestamp == 15;";
	    HashMap<String, Object> expectedSeries = new HashMap<String, Object>();
	    expectedSeries.put("action", "select");
	    expectedSeries.put("function", "all");
	    Assertions.assertEquals(expectedSeries,queryService.parseQuery(queryTest));
	}
	
	@Test
	// BadQueryException : Test when the Select Query is not specified
	public void parseQuerySelectUnspecifiedBadQueryExceptionTest() {
	    String queryTest = "myseries;";
	    String expectedMessage = "Bad action provided";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Select Query is not correct
	public void parseQuerySelectSyntaxBadQueryExceptionTest() {
	    String queryTest = "selected;";
	    String expectedMessage = "Error in SELECT query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Series in Query is not specified
	public void parseQuerySelectSeriesBadQueryExceptionTest() {
	    String queryTest = "select from;";
	    String expectedMessage = "Error in SELECT query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}	
	
	@Test
	// BadQueryException : Test when the From in Query is incorrect
	public void parseQuerySelectFromSyntaxBadQueryExceptionTest() {
	    String queryTest = "select myseries rom;";
	    String expectedMessage = "Error in SELECT query";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Conditions in Query are not specified
	public void parseQuerySelectConditionsUnspecifiedBadQueryExceptionTest() {
	    String queryTest = "select all from myseries where;";
	    String expectedMessage = "Bad action provided";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Conditions Syntax are incorrect
	public void parseQuerySelectConditionsSyntaxBadQueryExceptionTest() {
	    String queryTest = "select all from myseries were timestamp == 15;";
	    String expectedMessage = "Bad action provided";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Conditions Syntax are incorrect
	public void parseQuerySelectConditionsSyntaxBracketBadQueryExceptionTest() {
	    String queryTest = "select all from myseries where timestamp == (15);";
	    String expectedMessage = "Error in conditions 0";
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Test when the Query is correct
	public void parseQuerySelectTest() throws BadQueryException {
	    String queryTest = "select all from myseries where timestamp == 15;";
		HashMap<String, Object> expectedMap = new HashMap<String, Object>();
		expectedMap.put("action", "select");
		expectedMap.put("series", "myseries where;");
		expectedMap.put("function","all");
	    Assertions.assertEquals(expectedMap,  queryService.parseQuery(queryTest));
	}
	
	@Test
	public void parseQueryBadQueryExceptionBadActionProvidedTest() {
	    String query = "CREATT MySeries int64;";
	    String expectedMessage = "Bad action provided";
	        
	    Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(query));
	    Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	// ---------------------- [SELECT] [SINGLEQUERY] [OK]
	
}
