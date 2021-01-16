package fr.tse.db.query.service;

import fr.tse.db.query.error.BadQueryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class QueryParsingCreateTests {

	private final static String ACTION = "create";
	@Autowired
	private QueryService qs;

	// ---------------------- [CREATE] [SINGLEQUERY]
	@Test
	public void parseQuerySingleCreate() throws BadQueryException {
		// Testing the creation of a series with int32 type
		String queryInt32 = "create MySeries int32";

		HashMap<String, Object> hashMapExpected = new HashMap<>();
		hashMapExpected.put("name", "MySeries");
		hashMapExpected.put("action", "create");
		hashMapExpected.put("type", "int32");

		Assertions.assertEquals(hashMapExpected, qs.parseQuery(queryInt32));

		// Testing the creation of a series with int64 type
		String queryInt64 = "create MySeries int64";

		hashMapExpected.clear();
		hashMapExpected.put("name", "MySeries");
		hashMapExpected.put("action", "create");
		hashMapExpected.put("type", "int64");

		Assertions.assertEquals(hashMapExpected, qs.parseQuery(queryInt64));

		// Testing the creation of a series with float32 type
		String queryFloat32 = "create MySeries float32";

		hashMapExpected.clear();
		hashMapExpected.put("name", "MySeries");
		hashMapExpected.put("action", "create");
		hashMapExpected.put("type", "float32");

		Assertions.assertEquals(hashMapExpected, qs.parseQuery(queryFloat32));
	}

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
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CREATE_GENERAL;

		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(ACTION));
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
	// BadQueryException : special characters in name.
	public void parseQuerySingleCreateSyntax5BadQueryExceptionTest() {
		String query = ACTION + " MaSérie int64";
		String expectedMessage = BadQueryException.ERROR_MESSAGE_CREATE_IN_NAME_SPECIAL_CHARACTERS;

		Exception e = Assertions.assertThrows(BadQueryException.class, () -> qs.parseQuery(query));
		Assertions.assertEquals(expectedMessage, e.getMessage());
	}

	// ---------------------- [CREATE] [SINGLEQUERY] [OK]
	@Test
	// BadQueryException : Regular creation.
	public void parseQuerySingleCreateExample1Test() throws BadQueryException {
		String query = ACTION + " MySeries int64";
		HashMap<String, Object> expectedHashMap = new HashMap();
		expectedHashMap.put("action", "create");
		expectedHashMap.put("name", "MySeries");
		expectedHashMap.put("type", "int64");

		HashMap<String, Object> returnedHashMap = qs.parseQuery(query);
		Assertions.assertEquals(expectedHashMap, returnedHashMap);
	}

	@Test
	// BadQueryException : Regular creation with spaces.
	public void parseQuerySingleCreateExample2Test() throws BadQueryException {
		String query = ACTION + "    MySeries     int64";
		HashMap<String, Object> expectedHashMap = new HashMap();
		expectedHashMap.put("action", "create");
		expectedHashMap.put("name", "MySeries");
		expectedHashMap.put("type", "int64");

		HashMap<String, Object> returnedHashMap = qs.parseQuery(query);
		Assertions.assertEquals(expectedHashMap, returnedHashMap);
	}

}
