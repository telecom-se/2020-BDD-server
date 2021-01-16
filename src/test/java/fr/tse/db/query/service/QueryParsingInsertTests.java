package fr.tse.db.query.service;


import fr.tse.db.query.error.BadQueryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryParsingInsertTests {

    private final static String ACTION = "insert";
    private final QueryService qs = new QueryService();

    // ---------------------- [INSERT] [SINGLEQUERY] [BadQueryException]
    @Test
    // BadQueryException : Missing into.
    public void parseQuerySingleInsertSyntax1BadQueryExceptionTest() {
        String query = ACTION + " MySeries values ((300000,10))";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Missing name of the series.
    public void parseQuerySingleInsertSyntax2BadQueryExceptionTest() {
        String query = ACTION + " into values ((300000,10))";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Missing VALUES keyword.
    public void parseQuerySingleInsertSyntax3BadQueryExceptionTest() {
        String query = ACTION + " into MySeries ((300000,10))";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Missing values.
    public void parseQuerySingleInsertSyntax4BadQueryExceptionTest() {
        String query = ACTION + " into MySeries values";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Missing first level brackets.
    public void parseQuerySingleInsertSyntax5BadQueryExceptionTest() {
        String query = ACTION + " into MySeries values (300000,10)";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Missing first and second level brackets.
    public void parseQuerySingleInsertSyntax6BadQueryExceptionTest() {
        String query = ACTION + " into MySeries values 300000,10";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Unbalanced right brackets.
    public void parseQuerySingleInsertSyntax7BadQueryExceptionTest() {
        String query = ACTION + " into MySeries values ((300000,10)";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Unbalanced left brackets.
    public void parseQuerySingleInsertSyntax8BadQueryExceptionTest() {
        String query = ACTION + " into MySeries values (300000,10))";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_GENERAL;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Wrong values separator 1 ';'.
    public void parseQuerySingleInsertSyntax9BadQueryExceptionTest() {
        String query = ACTION + " into MySeries values ((300000;10))";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_IN_VALUES;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Wrong values separator 1 ' '.
    public void parseQuerySingleInsertSyntax10BadQueryExceptionTest() {
        String query = ACTION + " into MySeries values ((300000 10))";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_IN_VALUES;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Wrong value type 1.
    public void parseQuerySingleInsertSyntax11BadQueryExceptionTest() {
        String query = ACTION + " into MySeries values ((UnknownValue,10))";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_IN_TYPE;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Wrong value type 2.
    public void parseQuerySingleInsertSyntax12BadQueryExceptionTest() {
        String query = ACTION + " into MySeries values ((300000,UnknownValue))";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_IN_TYPE;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }


    @Test
    // BadQueryException : Too few values.
    public void parseQuerySingleInsertSyntax13BadQueryExceptionTest() {
        String query = ACTION + " into MySeries values ((300000))";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_IN_VALUES;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Too many values.
    public void parseQuerySingleInsertSyntax14BadQueryExceptionTest() {
        String query = ACTION + " into MySeries values ((300000,10,42))";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_INSERT_IN_VALUES;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    // ---------------------- [INSERT] [SINGLEQUERY] [OK]
    @Test
    // OK : Single insertion.
    public void parseQuerySingleInsertExample1Test() throws BadQueryException {
        String query = ACTION + " into MySeries values ((300000,10))";
        String[] pair = {"300000", "10"};
        ArrayList<String[]> values = new ArrayList<>();
        values.add(pair);

        HashMap<String, Object> expectedHashMap = new HashMap();
        expectedHashMap.put("action", "insert");
        expectedHashMap.put("series", "MySeries");
        expectedHashMap.put("pairs", values);

        HashMap<String, Object> returnedHashMap = qs.parseQuery(query);
        assertEquals(expectedHashMap.get("action"), returnedHashMap.get("action"));
        assertEquals(expectedHashMap.get("series"), returnedHashMap.get("series"));
        assertEquals(((ArrayList<String[]>) expectedHashMap.get("pairs")).get(0)[0], ((ArrayList<String[]>) returnedHashMap.get("pairs")).get(0)[0]);
        assertEquals(((ArrayList<String[]>) expectedHashMap.get("pairs")).get(0)[1], ((ArrayList<String[]>) returnedHashMap.get("pairs")).get(0)[1]);
    }

    // ---------------------- [INSERT] [MULTIPLEQUERY] [OK]
    @Test
    // OK : Multiple insertion.
    public void parseQueryMultipleInsertExample1Test() throws BadQueryException {
        String query = ACTION + " into MySeries values ((300000,10), (310000,100), (410000,11))";
        String[] pair1 = {"300000", "10"};
        String[] pair2 = {"310000", "100"};
        String[] pair3 = {"410000", "11"};
        ArrayList<String[]> values = new ArrayList<>();
        values.add(pair1);
        values.add(pair2);
        values.add(pair3);

        HashMap<String, Object> expectedHashMap = new HashMap();
        expectedHashMap.put("action", "insert");
        expectedHashMap.put("series", "MySeries");
        expectedHashMap.put("pairs", values);

        HashMap<String, Object> returnedHashMap = qs.parseQuery(query);
        assertEquals(expectedHashMap.get("action"), returnedHashMap.get("action"));
        assertEquals(expectedHashMap.get("series"), returnedHashMap.get("series"));
        int numberOfPairs = ((ArrayList<String[]>) expectedHashMap.get("pairs")).size();
        for (int i = 0; i < numberOfPairs; i++) {
            assertEquals(((ArrayList<String[]>) expectedHashMap.get("pairs")).get(i)[0], ((ArrayList<String[]>) returnedHashMap.get("pairs")).get(i)[0]);
            assertEquals(((ArrayList<String[]>) expectedHashMap.get("pairs")).get(i)[1], ((ArrayList<String[]>) returnedHashMap.get("pairs")).get(i)[1]);
        }
    }
}
