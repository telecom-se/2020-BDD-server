package fr.tse.db.query;

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
class QueryParsingCreateTests {

	@Autowired
	private QueryService qs;
	private final static String ACTION = "create";
	
	// ---------------------- [CREATE] [SINGLEQUERY] [BadQueryException]
	@Test
	// BadQueryException : Missing name.
	public void parseQuerySingleCreateSyntax1BadQueryExceptionTest() {
		String query = ACTION + " int64";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CREATE_GENERAL;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing type.
	public void parseQuerySingleCreateSyntax2BadQueryExceptionTest() {
		String query = ACTION + " MySeries";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CREATE_GENERAL;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Missing name and type.
	public void parseQuerySingleCreateSyntax3BadQueryExceptionTest() {
		String query = ACTION;
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CREATE_GENERAL;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : Unknown type.
	public void parseQuerySingleCreateSyntax4BadQueryExceptionTest() {
		String query = ACTION + " MySeries uintlong42";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CREATE_IN_TYPE;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : spaces in name.
	public void parseQuerySingleCreateSyntax5BadQueryExceptionTest() {
		String query = ACTION + " MySe ries int64";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CREATE_IN_NAME_SPACES;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	
	@Test
	// BadQueryException : special characters in name.
	public void parseQuerySingleCreateSyntax6BadQueryExceptionTest() {
		String query = ACTION + " MaSérie int64";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CREATE_IN_NAME_SPECIAL_CHARACTERS;
		
		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}
	// ---------------------- [CREATE] [SINGLEQUERY] [OK]
	// TODO
}
