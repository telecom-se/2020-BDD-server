package fr.tse.db.query.service;

import fr.tse.db.query.error.BadQueryException;
import fr.tse.db.storage.data.DataBase;
import fr.tse.db.storage.data.Int32;
import fr.tse.db.storage.data.Series;
import fr.tse.db.storage.data.SeriesUncompressed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class QueryParsingSelectTests {

    private final QueryService queryService = new QueryService();

    @Test
    // BadQueryException : Test when the Select Query is correct
    public void parseQuerySelectSyntaxQueryExceptionTest() throws BadQueryException {
        String queryTest = "select all from myseries where timestamp == 15;";
        HashMap<String, Object> expectedSeries = new HashMap<>();
        expectedSeries.put("action", "select");
        expectedSeries.put("function", "all");
        expectedSeries.put("series", "myseries");
        expectedSeries.put("operators", Collections.singletonList("=="));
        expectedSeries.put("timestamps", Collections.singletonList(15L));
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
        expectedMap.put("timestamps", Collections.singletonList((15L)));
        expectedMap.put("operators", Collections.singletonList("=="));
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
        db.addSeries(new SeriesUncompressed("MySeries1", Int32.class));

        try {
            String queryTest = "SELECT ALL FROM MySeries1";
            HashMap<String, Object> response = queryService.handleQuery(queryTest);

            Assertions.assertNotNull(response.get("values"));
            ArrayList<Map<String, Object>> res = (ArrayList<Map<String, Object>>) response.get("values");
            Assertions.assertEquals(0, res.size());

            db.deleteSeries("MySeries1");
        } catch (Exception e) {
            db.deleteSeries("MySeries1");
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void handleQuerySelectSimpleWhere() {

        DataBase db = DataBase.getInstance();
        Series addedSerie = new SeriesUncompressed("MySeries1", Int32.class);
        addedSerie.addPoint(15L, new Int32(3));
        addedSerie.addPoint(12L, new Int32(34));
        db.addSeries(addedSerie);

        try {
            String queryTest = "SELECT ALL FROM MySeries1 WHERE TIMESTAMP == 15";
            HashMap<String, Object> response = queryService.handleQuery(queryTest);
            Assertions.assertNotNull(response.get("values"));
            ArrayList<Map<String, Object>> res = (ArrayList<Map<String, Object>>) response.get("values");

            Assertions.assertEquals(1, res.size());
            Assertions.assertEquals(3, res.get(0).get("value"));
            db.deleteSeries("MySeries1");

        } catch (Exception e) {
            db.deleteSeries("MySeries1");
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void handleQuerySelectWhereCondition() throws Exception {
        DataBase db = DataBase.getInstance();
        Series addedSerie = new SeriesUncompressed("MySeries1", Int32.class);
        addedSerie.addPoint(12L, new Int32(12));
        addedSerie.addPoint(15L, new Int32(15));
        addedSerie.addPoint(17L, new Int32(17));
        addedSerie.addPoint(18L, new Int32(18));
        addedSerie.addPoint(19L, new Int32(19));
        db.addSeries(addedSerie);

        String queryTest = "SELECT ALL FROM MySeries1 WHERE TIMESTAMP >= 15";
        HashMap<String, Object> response = queryService.handleQuery(queryTest);
        Assertions.assertNotNull(response.get("values"));
        ArrayList<Map<String, Object>> res = (ArrayList<Map<String, Object>>) response.get("values");

        Assertions.assertEquals(4, res.size());
        Assertions.assertEquals(15, res.stream().min(Comparator.comparing((Map<String, Object> e) -> ((Long) e.get("timestamp")))).get().get("value"));
        db.deleteSeries("MySeries1");
    }

    @Test
    public void handleQuerySelectMinWithCondition() {

        DataBase db = DataBase.getInstance();
        Series addedSerie = new SeriesUncompressed("MySeries1", Int32.class);
        addedSerie.addPoint(16L, new Int32(3));
        addedSerie.addPoint(12L, new Int32(1));
        addedSerie.addPoint(21L, new Int32(2));
        addedSerie.addPoint(18L, new Int32(4));
        db.addSeries(addedSerie);

        try {
            String queryTest1 = "SELECT MIN FROM MySeries1 WHERE TIMESTAMP > 15";
            HashMap<String, Object> response1 = queryService.handleQuery(queryTest1);
            Assertions.assertNotNull(response1.get("min"));
            Assertions.assertEquals(2, response1.get("min"));

            String queryTest2 = "SELECT MIN FROM MySeries1 WHERE TIMESTAMP < 17";
            HashMap<String, Object> response2 = queryService.handleQuery(queryTest2);
            Assertions.assertNotNull(response2.get("min"));
            Assertions.assertEquals(1, response2.get("min"));

            db.deleteSeries("MySeries1");


        } catch (Exception e) {
            db.deleteSeries("MySeries1");
            Assertions.fail(e.getMessage());
        }

    }

    // NOT WORKING ONLY THE FIRST CONDITION IS TAKEN INTO ACCOUNT
    @Test
    public void handleQuerySelectMinWithMultipleCondition() {

        DataBase db = DataBase.getInstance();
        Series addedSeries = new SeriesUncompressed("MySeries1", Int32.class);
        addedSeries.addPoint(12L, new Int32(1));
        addedSeries.addPoint(16L, new Int32(3));
        addedSeries.addPoint(18L, new Int32(4));
        addedSeries.addPoint(21L, new Int32(2));
        db.addSeries(addedSeries);

        try {
            String queryTest3 = "SELECT MIN FROM MySeries1 WHERE TIMESTAMP > 15 AND TIMESTAMP < 20";
            HashMap<String, Object> response3 = queryService.handleQuery(queryTest3);
            Assertions.assertNotNull(response3.get("min"));
            Assertions.assertEquals(3, response3.get("min"));
            db.deleteSeries("MySeries1");
        } catch (Exception e) {
            db.deleteSeries("MySeries1");
            Assertions.fail(e.getMessage());
        }

    }

}
