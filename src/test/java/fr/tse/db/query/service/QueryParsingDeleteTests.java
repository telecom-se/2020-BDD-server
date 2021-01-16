package fr.tse.db.query.service;

import fr.tse.db.query.error.BadQueryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class QueryParsingDeleteTests {

    private final static String ACTION = "delete";
    private final QueryService qs = new QueryService();

    // ---------------------- [DELETE] [SINGLEQUERY] [BadQueryException]
    @Test
    // BadQueryException : Missing the name of the series.
    public void parseQuerySingleDeleteSyntax1BadQueryExceptionTest() {
        String query = ACTION + " from where TIMESTAMP == 12525";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_DELETE_IN_NAME;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Missing conditions after "WHERE".
    public void parseQuerySingleDeleteSyntax2BadQueryExceptionTest() {
        String query = ACTION + " from MySeries where";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;
        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertTrue(e.getMessage().contains(expectedMessage));
    }

    @Test
    // BadQueryException : Unknown attribute.
    public void parseQuerySingleDeleteSyntax3BadQueryExceptionTest() {
        String query = ACTION + " from MySeries where unknownattribute == 12525";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertTrue(e.getMessage().contains(expectedMessage));
    }

    @Test
    // BadQueryException : Unknown operator.
    public void parseQuerySingleDeleteSyntax4BadQueryExceptionTest() {
        String query = ACTION + " from MySeries where timestamp $ 12525";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertTrue(e.getMessage().contains(expectedMessage));
    }

    @Test
    // BadQueryException : Several operators in a row.
    public void parseQuerySingleDeleteSyntax5BadQueryExceptionTest() {
        String query = ACTION + " from MySeries where timestamp > == 12525";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertTrue(e.getMessage().contains(expectedMessage));
    }

    @Test
    // BadQueryException : Not a numerical value after an operator 1.
    public void parseQuerySingleDeleteSyntax6BadQueryExceptionTest() {
        String query = ACTION + " from MySeries where timestamp > NaN";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertTrue(e.getMessage().contains(expectedMessage));
    }

    @Test
    // BadQueryException : Not a numerical value after an operator 2.
    public void parseQuerySingleDeleteSyntax7BadQueryExceptionTest() {
        String query = ACTION + " from MySeries where timestamp > \"NAN\"";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertTrue(e.getMessage().contains(expectedMessage));
    }

    @Test
    // BadQueryException : Unknown boolean operator.
    public void parseQuerySingleDeleteSyntax8BadQueryExceptionTest() {
        String query = ACTION + " from MySeries where value > 15 nor timestamp <= 15";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Several boolean operators in a row.
    public void parseQuerySingleDeleteSyntax9BadQueryExceptionTest() {
        String query = ACTION + " from MySeries where value > 15 and or timestamp <= 15";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_TOO_MANY;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Unknown attribute after boolean operator.
    public void parseQuerySingleDeleteSyntax10BadQueryExceptionTest() {
        String query = ACTION + " from MySeries where value > 15 and unknownattribute <= 15";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertTrue(e.getMessage().contains(expectedMessage));
    }

    @Test
    // BadQueryException : Unknown operator after boolean operator.
    public void parseQuerySingleDeleteSyntax11BadQueryExceptionTest() {
        String query = ACTION + " from MySeries where value > 15 and timestamp $ 15";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertTrue(e.getMessage().contains(expectedMessage));
    }

    @Test
    // BadQueryException : Several operators after boolean operator.
    public void parseQuerySingleDeleteSyntax12BadQueryExceptionTest() {
        String query = ACTION + " from MySeries where value > 15 and timestamp > == 15";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertTrue(e.getMessage().contains(expectedMessage));
    }

    @Test
    // BadQueryException : Not a numerical value after boolean operator 1.
    public void parseQuerySingleDeleteSyntax13BadQueryExceptionTest() {
        String query = ACTION + " from MySeries where value > 15 and timestamp <= Nan";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertTrue(e.getMessage().contains(expectedMessage));
    }

    @Test
    // BadQueryException : Not a numerical value after boolean operator 2.
    public void parseQuerySingleDeleteSyntax14BadQueryExceptionTest() {
        String query = ACTION + " from MySeries where value > 15 and timestamp <= \"Nan\"";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertTrue(e.getMessage().contains(expectedMessage));
    }

    @Test
    // BadQueryException : Missing ALL or FROM.
    public void parseQuerySingleDeleteSyntax15BadQueryExceptionTest() {
        String expectedMessage = BadQueryException.ERROR_MESSAGE_DELETE_GENERAL_1;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(ACTION));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : No Series name specified.
    public void parseQuerySingleDeleteSyntax16BadQueryExceptionTest() {
        String query = ACTION + " from ";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_DELETE_IN_NAME;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    // ---------------------- [DELETE] [OK]
    @Test
    // Delete a Series
    public void parseQueryDeleteExample1Test() throws BadQueryException {
        String query = ACTION + " from MySeries";
        HashMap<String, Object> expectedHashMap = new HashMap();
        expectedHashMap.put("series", "MySeries");
        expectedHashMap.put("action", "delete");

        HashMap<String, Object> returnedHashMap = qs.parseQuery(query);
        Assertions.assertEquals(expectedHashMap, returnedHashMap);
    }

    @Test
    // Delete with condition
    public void parseQueryDeleteExample2Test() throws BadQueryException {
        String query = ACTION + " from MySeries where timestamp == 12525";
        HashMap<String, Object> expectedHashMap = new HashMap();
        expectedHashMap.put("timestamps", Collections.singletonList((long) 12525));
        expectedHashMap.put("operators", Collections.singletonList("=="));
        expectedHashMap.put("join", null);
        expectedHashMap.put("series", "MySeries");
        expectedHashMap.put("action", "delete");

        HashMap<String, Object> returnedHashMap = qs.parseQuery(query);
        Assertions.assertEquals(expectedHashMap, returnedHashMap);
    }

    @Test
    // Delete with several conditions
    public void parseQueryDeleteExample3Test() throws BadQueryException {
        String query = ACTION + " from MySeries where timestamp > 5 and timestamp < 15";
        HashMap<String, Object> expectedHashMap = new HashMap();
        HashMap<String, Object> whereConditions = new HashMap<>();
        List<String> operators = new ArrayList<>();
        operators.add(">");
        operators.add("<");
        List<Long> timestamps = new ArrayList<>();
        timestamps.add((long) 5);
        timestamps.add((long) 15);
        expectedHashMap.put("timestamps", timestamps);
        expectedHashMap.put("operators", operators);
        expectedHashMap.put("join", "and");
        expectedHashMap.put("series", "MySeries");
        expectedHashMap.put("action", "delete");

        HashMap<String, Object> returnedHashMap = qs.parseQuery(query);
        Assertions.assertEquals(expectedHashMap, returnedHashMap);
    }

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
        Assertions.assertEquals(hashMap.get("operators"), Collections.singletonList("=="));
        Assertions.assertEquals(hashMap.get("timestamps"), Collections.singletonList(3000L));
    }

    @Test
    public void parseQuerySingleDeleteSyntax3NoExceptionTest() throws BadQueryException {
        String seriesName = "TestSerieInt32";
        String query = ACTION + " from " + seriesName + " where timestamp == 3000";

        HashMap<String, Object> hashMap = qs.parseQuery(query);

        // Check valid hashmap
        Assertions.assertEquals(hashMap.get("action"), "delete");
        Assertions.assertEquals(hashMap.get("series"), seriesName);
        Assertions.assertEquals(hashMap.get("operators"), Collections.singletonList("=="));
        Assertions.assertEquals(hashMap.get("timestamps"), Collections.singletonList(3000L));
    }

    // ---------------------- [DELETE] [SINGLEQUERY]

}
