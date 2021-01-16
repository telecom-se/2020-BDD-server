package fr.tse.db.query;


import fr.tse.db.query.error.BadQueryException;
import fr.tse.db.query.service.QueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QueryApplicationTests {

    @Autowired
    private QueryService qs;

    @Test
    void contextLoads() {
    }


    @Test
    // Unknown action in query
    public void parseQueryBadQueryExceptionBadActionProvidedTest() {
        String query = "CREATT MySeries int64";
        String expectedMessage = BadQueryException.ERROR_MESSAGE_BAD_ACTION;

        Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
        Assertions.assertEquals(expectedMessage, e.getMessage());
    }

}
