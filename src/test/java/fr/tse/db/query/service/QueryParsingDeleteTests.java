package fr.tse.db.query.service;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.tse.db.query.error.BadQueryException;
import fr.tse.db.query.service.QueryService;
import fr.tse.db.storage.data.Int32;
import fr.tse.db.storage.data.Series;
import fr.tse.db.storage.request.Requests;
import fr.tse.db.storage.request.RequestsImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
class QueryParsingDeleteTests {

	@Autowired
	private QueryService qs;
	private final static String ACTION = "delete";
	
	// ---------------------- [DELETE ALL] [SINGLEQUERY] [BadQueryException]
	@Test
	// BadQueryException : Missing ';' at the end of the query.
	public void parseQuerySingleDeleteAllSyntax1BadQueryExceptionTest() {
		String query = ACTION + " ALL FROM MySeries";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing "FROM" after "ALL".
	public void parseQuerySingleDeleteAllSyntax2BadQueryExceptionTest() {
		String query = ACTION + " ALL MySeries;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing name of the series to DELETE ALL.
	public void parseQuerySingleDeleteAllSyntax3BadQueryExceptionTest() {
		String query = ACTION + " ALL FROM;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : "FROM" and "ALL" inverted.
	public void parseQuerySingleDeleteAllSyntax4BadQueryExceptionTest() {
		String query = ACTION + " FROM ALL MySeries";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	// ---------------------- [DELETE ALL] [SINGLEQUERY] [OK]
	// TODO
	
	
	// ---------------------- [DELETE] [SINGLEQUERY] [BadQueryException]
	@Test
	// BadQueryException : Missing ';' at the end of the query.
	public void parseQuerySingleDeleteSyntax1BadQueryExceptionTest() {
		String query = ACTION + " FROM MySeries WHERE TIMESTAMP == 12525";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing the name of the series.
	public void parseQuerySingleDeleteSyntax2BadQueryExceptionTest() {
		String query = ACTION + " FROM WHERE TIMESTAMP == 12525;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing "WHERE" clause.
	public void parseQuerySingleDeleteSyntax3BadQueryExceptionTest() {
		String query = ACTION + " FROM MySeries;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing conditions after "WHERE".
	public void parseQuerySingleDeleteSyntax4BadQueryExceptionTest() {
		String query = ACTION + " FROM MySeries WHERE;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Unknown attribute.
	public void parseQuerySingleDeleteSyntax5BadQueryExceptionTest() {
		String query = ACTION + " FROM MySeries WHERE UNKNOWNATTRIBUTE == 12525;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Unknown operator.
	public void parseQuerySingleDeleteSyntax6BadQueryExceptionTest() {
		String query = ACTION + " FROM MySeries WHERE UNKNOWNATTRIBUTE $ 12525;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Several operators in a row.
	public void parseQuerySingleDeleteSyntax7BadQueryExceptionTest() {
		String query = ACTION + " FROM MySeries WHERE UNKNOWNATTRIBUTE > == 12525;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Not a numerical value after an operator 1.
	public void parseQuerySingleDeleteSyntax8BadQueryExceptionTest() {
		String query = ACTION + " FROM MySeries WHERE UNKNOWNATTRIBUTE > NaN;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Not a numerical value after an operator 2.
	public void parseQuerySingleDeleteSyntax9BadQueryExceptionTest() {
		String query = ACTION + " FROM MySeries WHERE UNKNOWNATTRIBUTE > \"NAN\";";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Unknown boolean operator.
	public void parseQuerySingleDeleteSyntax10BadQueryExceptionTest() {
		String query = ACTION + " FROM MySeries WHERE VALUE > 15 XORAND TIMESTAMP <= 15;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Several boolean operators in a row.
	public void parseQuerySingleDeleteSyntax11BadQueryExceptionTest() {
		String query = ACTION + " FROM MySeries WHERE VALUE > 15 AND OR TIMESTAMP <= 15;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Unknown attribute after boolean operator.
	public void parseQuerySingleDeleteSyntax12BadQueryExceptionTest() {
		String query = ACTION + " FROM MySeries WHERE VALUE > 15 AND UNKNOWNATTRIBUTE <= 15;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Unknown operator after boolean operator.
	public void parseQuerySingleDeleteSyntax13BadQueryExceptionTest() {
		String query = ACTION + " FROM MySeries WHERE VALUE > 15 AND TIMESTAMP $ 15;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Several operators after boolean operator.
	public void parseQuerySingleDeleteSyntax14BadQueryExceptionTest() {
		String query = ACTION + " FROM MySeries WHERE VALUE > 15 AND TIMESTAMP > == 15;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Not a numerical value after boolean operator 1.
	public void parseQuerySingleDeleteSyntax15BadQueryExceptionTest() {
		String query = ACTION + " FROM MySeries WHERE VALUE > 15 AND TIMESTAMP <= Nan;";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Not a numerical value after boolean operator 2.
	public void parseQuerySingleDeleteSyntax16BadQueryExceptionTest() {
		String query = ACTION + " FROM MySeries WHERE VALUE > 15 AND TIMESTAMP <= \"Nan\";";
		String expectedMessage = "Error in query";
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	// ---------------------- [DELETE] [SINGLEQUERY] [OK]
	
	
	
	// ---------------------- [DELETE] [SINGLEQUERY] 
	
	@Test
	public void parseQuerySingleDeleteSyntax1NoExceptionTest() throws BadQueryException {
		String seriesName = "TestSerieInt32";
		String query = ACTION + " from " + seriesName;

		HashMap<String, Object> hashMap = qs.parseQuery(query);
		
		// Check valid hashmap
		Assertions.assertEquals(hashMap.get("action"), "delete");
		Assertions.assertEquals(hashMap.get("series"), seriesName);
	}
	
	
	@Test
	public void parseQuerySingleDeleteSyntax2NoExceptionTest() throws BadQueryException {
		String seriesName = "TestSerieInt32";
		String query = ACTION + " from " + seriesName + " where timestamp == 3000";
		
		HashMap<String, Object> hashMap = qs.parseQuery(query);
		
		// Check valid hashmap
		Assertions.assertEquals(hashMap.get("action"), "delete");
		Assertions.assertEquals(hashMap.get("series"), seriesName);
		Assertions.assertEquals(hashMap.get("operators"), Arrays.asList(new String[]{"=="}));
		Assertions.assertEquals(hashMap.get("timestamps"), Arrays.asList(new Long[]{3000L}));
	}
	
	@Test
	public void parseQuerySingleDeleteSyntax3NoExceptionTest() throws BadQueryException {
		String seriesName = "TestSerieInt32";
		String query = ACTION + " from " + seriesName + " where timestamp == 3000";
		
		HashMap<String, Object> hashMap = qs.parseQuery(query);
		
		System.out.println(hashMap);
		
		// Check valid hashmap
		Assertions.assertEquals(hashMap.get("action"), "delete");
		Assertions.assertEquals(hashMap.get("series"), seriesName);
		Assertions.assertEquals(hashMap.get("operators"), Arrays.asList(new String[]{"=="}));
		Assertions.assertEquals(hashMap.get("timestamps"), Arrays.asList(new Long[]{3000L}));
	}

	// ---------------------- [DELETE] [SINGLEQUERY] 
	
}
