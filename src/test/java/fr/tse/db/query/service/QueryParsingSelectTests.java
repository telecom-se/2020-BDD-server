package fr.tse.db.query.service;

import fr.tse.db.query.error.BadQueryException;
import fr.tse.db.storage.data.DataBase;
import fr.tse.db.storage.data.Int32;
import fr.tse.db.storage.data.Series;
import fr.tse.db.storage.data.SeriesUnComp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;

@SpringBootTest
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
		expectedSeries.put("series", "myseries");
		expectedSeries.put("operators", Arrays.asList("=="));
		expectedSeries.put("timestamps", Arrays.asList(15L));
		expectedSeries.put("join", null);
		Assertions.assertEquals(expectedSeries, queryService.parseQuery(queryTest));
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
		String expectedMessage = "Error in SELECT query";
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}

	@Test
	// BadQueryException : Test when the Conditions Syntax are incorrect
	public void parseQuerySelectConditionsSyntaxBadQueryExceptionTest() {
		String queryTest = "select all from myseries were timestamp == 15;";
		String expectedMessage = "Error in SELECT query";
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}

	@Test
	// BadQueryException : Test when the Conditions Syntax are incorrect
	public void parseQuerySelectConditionsSyntaxBracketBadQueryExceptionTest() {
		String queryTest = "select all from myseries where timestamp == (15);";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}

	@Test
	// BadQueryException : Test when the Query is correct
	public void parseQuerySelectTest() throws BadQueryException {
		String queryTest = "select all from myseries where timestamp == 15;";
		HashMap<String, Object> expectedMap = new HashMap();
		expectedMap.put("action", "select");
		expectedMap.put("series", "myseries");
		expectedMap.put("timestamps", Arrays.asList((15L)));
		expectedMap.put("operators", Arrays.asList("=="));
		expectedMap.put("join", null);
		expectedMap.put("function", "all");
		HashMap<String, Object> actualMap = queryService.parseQuery(queryTest);
		Assertions.assertEquals(expectedMap, actualMap);
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
	public void handleQuerySelectOkAll() {

		DataBase db = DataBase.getInstance();
		db.addSeries(new SeriesUnComp("myseries1", Int32.class));

		try {
			String queryTest = "SELECT ALL FROM MySeries1";
			HashMap<String, Object> response = queryService.handleQuery(queryTest);

			Assertions.assertNotNull(response.get("values"));
			Series seriesResult = (Series) response.get("values");
			Assertions.assertEquals("myseries1", seriesResult.getName());

			db.deleteSeries("myseries1");
		} catch (Exception e) {
			db.deleteSeries("myseries1");
			Assertions.fail(e.getMessage());
		}
	}

	@Test
	public void handleQuerySelectSimpleWhere() {

		DataBase db = DataBase.getInstance();
		Series addedSerie = new SeriesUnComp("myseries1", Int32.class);
		addedSerie.addPoint(15L, new Int32(3));
		addedSerie.addPoint(12L, new Int32(34));
		db.addSeries(addedSerie);

		try {
			String queryTest = "SELECT ALL FROM MySeries1 WHERE TIMESTAMP == 15";
			HashMap<String, Object> response = queryService.handleQuery(queryTest);
			Assertions.assertNotNull(response.get("values"));
			HashMap<Long, Int32> points = (HashMap<Long, Int32>) ((Series) response.get("values")).getPoints();

			Assertions.assertEquals(1, points.size());
			Assertions.assertNotNull(points.get(15L));
			db.deleteSeries("myseries1");

		} catch (Exception e) {
			db.deleteSeries("myseries1");
			Assertions.fail(e.getMessage());
		}


	}

	@Test
	public void handleQuerySelectMinWithCondition() {

		DataBase db = DataBase.getInstance();
		Series addedSerie = new SeriesUnComp("myseries1", Int32.class);
		addedSerie.addPoint(16L, new Int32(3));
		addedSerie.addPoint(12L, new Int32(1));
		addedSerie.addPoint(21L, new Int32(2));
		addedSerie.addPoint(18L, new Int32(4));
		db.addSeries(addedSerie);

		try {
			String queryTest1 = "SELECT MIN FROM MySeries1 WHERE TIMESTAMP > 15";
			HashMap<String, Object> response1 = queryService.handleQuery(queryTest1);
			Assertions.assertNotNull(response1.get("min"));
			Assertions.assertEquals(2, ((Int32) response1.get("min")).getVal());

			String queryTest2 = "SELECT MIN FROM MySeries1 WHERE TIMESTAMP < 17";
			HashMap<String, Object> response2 = queryService.handleQuery(queryTest2);
			Assertions.assertNotNull(response2.get("min"));
			Assertions.assertEquals(1, ((Int32) response2.get("min")).getVal());

			db.deleteSeries("myseries1");


		} catch (Exception e) {
			db.deleteSeries("myseries1");
			Assertions.fail(e.getMessage());
		}

	}

	// NOT WORKING ONLY THE FIRST CONDITION IS TAKEN INTO ACCOUNT
	@Test
	public void handleQuerySelectMinWithMultipleCondition() {

		DataBase db = DataBase.getInstance();
		Series addedSeries = new SeriesUnComp("myseries1", Int32.class);
		addedSeries.addPoint(16L, new Int32(3));
		addedSeries.addPoint(12L, new Int32(1));
		addedSeries.addPoint(21L, new Int32(2));
		addedSeries.addPoint(18L, new Int32(4));
		db.addSeries(addedSeries);

		try {
			String queryTest3 = "SELECT MIN FROM MySeries1 WHERE TIMESTAMP > 15 AND TIMESTAMP < 20";
			HashMap<String, Object> response3 = queryService.handleQuery(queryTest3);
			Assertions.assertNotNull(response3.get("min"));
			Assertions.assertEquals(3, ((Int32) response3.get("min")).getVal());
			db.deleteSeries("myseries1");
		} catch (Exception e) {
			db.deleteSeries("myseries1");
			Assertions.fail(e.getMessage());
		}

	}

}
