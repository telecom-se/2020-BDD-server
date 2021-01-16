package fr.tse.db.query.service;

import fr.tse.db.query.error.BadQueryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class QueryParsingShowTests {
        private final QueryService queryService = new QueryService();

    @Test
    public void showAllTest() throws BadQueryException {
        String queryTest = "show all";
        HashMap<String, Object> expectedSeries = new HashMap<>();
        expectedSeries.put("action", "show");
        expectedSeries.put("series", "all");
        Assertions.assertEquals(expectedSeries, queryService.parseQuery(queryTest));
    }

    @Test
    // BadQueryException : Test when the Show Query is correct
    public void parseQueryShowSyntaxQueryExceptionTest() {
        String queryTest = "show";
        String expectedMessage = "Error in SHOW query";
        Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Test when the Show Query is not specified
    public void parseQueryShowUnspecifiedBadQueryExceptionTest() {
        String queryTest = "myseries;";
        String expectedMessage = "Bad action provided";
        Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Test when the Show Query is not correct
    public void parseQueryShowSyntaxBadQueryExceptionTest() {
        String queryTest = "showed;";
        String expectedMessage = "Error in SHOW query";
        Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Test when the Series in Query is not specified
    public void parseQueryShowSeriesBadQueryExceptionTest() {
        String queryTest = "show from;";
        String expectedMessage = "Error in SHOW query";
        Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Test when the From in Query is incorrect
    public void parseQueryShowFromSyntaxBadQueryExceptionTest() {
        String queryTest = "show mySeries rom;";
        String expectedMessage = "Error in SHOW query";
        Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    // BadQueryException : Test when the Conditions in Query are not specified
    public void parseQueryShowConditionsUnspecifiedBadQueryExceptionTest() {
        String queryTest = "show all from myseries where;";
        String expectedMessage = "Error in SHOW query";
        Exception e = Assertions.assertThrows(BadQueryException.class, () -> queryService.parseQuery(queryTest));
        // Pb : queryService.parseQuery(quertyTest) ne renvoie pas d'exception
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }
}
