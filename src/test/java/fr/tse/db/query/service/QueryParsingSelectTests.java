package fr.tse.db.query.service;

import java.util.HashMap;
import java.util.Set;

import org.junit.Assert;
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
	
	// Query Ok Tests
	
	
	@Test
	public void parseQuerySelectOkAll() {
	    
		DataBase db = DataBase.getInstance();		
		db.addSeries(new Series("myseries1", Int32.class));
		
		try {
		String queryTest = "SELECT ALL FROM MySeries1";
	    HashMap<String, Object> response = queryService.handleQuery(queryTest);
	    
		Assertions.assertNotNull(response.get("values"));
		Series seriesResult = (Series) response.get("values");
		Assertions.assertEquals("myseries1", seriesResult.getName());

		db.deleteSeries("myseries1");
		}catch(Exception e) {
			db.deleteSeries("myseries1");
			Assert.fail(e.getMessage());
		}
		

		
	}
	
	// NOT WORKING : THE SERIES (myseries1, myseries2) is seeked instead of the series myseries1 and myseries2
	@Test
	public void parseQuerySelectOkAllMultiple()  {
	    
		DataBase db = DataBase.getInstance();
		
		db.addSeries(new Series("myseries1", Int32.class));
		db.addSeries(new Series("myseries2", Int32.class));
		
		try {
			String queryTest2 = "SELECT ALL FROM (MySeries1, myseries2)";

			HashMap<String, Object> response = queryService.handleQuery(queryTest2);
			Assertions.assertNotNull(response.get("values"));

			Series seriesResult = (Series) response.get("values");
			Assertions.assertEquals("myseries1", seriesResult.getName());

			db.deleteSeries("myseries1");
			db.deleteSeries("myseries2");

		}catch(Exception e) {
			db.deleteSeries("myseries1");
			db.deleteSeries("myseries2");
			Assert.fail(e.getMessage());
		}
		
		

	}
	
	
	@Test 
	public void parseQuerySelectSimpleWhere() {
		
		DataBase db = DataBase.getInstance();		
		Series addedSerie = new Series("myseries1", Int32.class);
		addedSerie.addPoint(15L, new Int32(3));
		addedSerie.addPoint(12L,new Int32(34));
		db.addSeries(addedSerie);
		
		try {
		String queryTest = "SELECT ALL FROM MySeries1 WHERE TIMESTAMP == 15";
		HashMap<String, Object> response = queryService.handleQuery(queryTest);
		Assertions.assertNotNull(response.get("values"));
		HashMap<Long,Int32> points = (HashMap<Long, Int32>) ((Series)response.get("values")).getPoints();
		
		Assertions.assertEquals(1, points.size());
		Assertions.assertNotNull(points.get(15L) );
		db.deleteSeries("myseries1");

		} catch(Exception e) {
			db.deleteSeries("myseries1");
			Assert.fail(e.getMessage());
		}
		

	}
	
	@Test
	public void parseQuerySelectMinFromRange()  {
		
		DataBase db = DataBase.getInstance();		
		Series addedSerie = new Series("myseries1", Int32.class);
		addedSerie.addPoint(16L, new Int32(3));
		addedSerie.addPoint(12L,new Int32(1));
		addedSerie.addPoint(21L,new Int32(2));
		addedSerie.addPoint(18L,new Int32(4));
		db.addSeries(addedSerie);

		try {
		String queryTest1 = "SELECT MIN FROM MySeries1 WHERE TIMESTAMP > 15";
	    HashMap<String, Object> response1 = queryService.handleQuery(queryTest1);
	    Assertions.assertNotNull(response1.get("min"));
		Assertions.assertEquals(2, ((Int32)response1.get("min")).getVal());
		
		String queryTest2 = "SELECT MIN FROM MySeries1 WHERE TIMESTAMP < 17";
	    HashMap<String, Object> response2 = queryService.handleQuery(queryTest2);
	    Assertions.assertNotNull(response2.get("min"));
		Assertions.assertEquals(1, ((Int32)response2.get("min")).getVal());
		
		
		String queryTest3 = "SELECT MIN FROM MySeries1 WHERE TIMESTAMP > 15 AND TIMESTAMP < 20";
	    HashMap<String, Object> response3 = queryService.handleQuery(queryTest3);    
	    Assertions.assertNotNull(response3.get("min"));
		Assertions.assertEquals(3, ((Int32)response3.get("min")).getVal());
		db.deleteSeries("myseries1");

		}
		catch(Exception e){
			db.deleteSeries("myseries1");
			Assert.fail(e.getMessage());
		}
		
		
		
		
		
		
	}
	
}
