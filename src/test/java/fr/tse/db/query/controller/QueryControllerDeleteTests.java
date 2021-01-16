package fr.tse.db.query.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fr.tse.db.storage.data.DataBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import fr.tse.db.storage.data.Int32;
import fr.tse.db.storage.data.Series;
import fr.tse.db.storage.request.Requests;
import fr.tse.db.storage.request.RequestsImpl;

import javax.xml.crypto.Data;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test_query_controller")
class QueryControllerDeleteTests {
	
	@Autowired
	private MockMvc mvc;
	
	private String seriesInt32 = "seriesInt32";
	
	private Requests request = new RequestsImpl();

	@Test
	public void testQueryControllerDeleteTests() throws Exception {

		DataBase db = DataBase.getInstance();

		this.mvc.perform(
				post("/query")
						.param("query", "DELETE FROM " + seriesInt32 + " WHERE TIMESTAMP == 1;")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success", is(true)));
		Assertions.assertFalse(db.getByName(seriesInt32.toLowerCase()).getPoints().keySet().contains(1L));


		// Exception not implemented in the current version of the software
		/*this.mvc.perform(
				post("/query")
						.param("query", "DELETE FROM " + seriesInt32 + " WHERE TIMESTAMP == 1;")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success", is(false)));*/

	}

	@Test
	public void testQueryControllerDeleteAllTest() throws Exception{
		DataBase db = DataBase.getInstance();
		this.mvc.perform( 
			     post("/query")
			    .param("query", "DELETE FROM " + seriesInt32)
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$.success", is(true)));

		Assertions.assertTrue(db.getByName(seriesInt32.toLowerCase()).getPoints().isEmpty());

		// Exception not implemented in the current version of the software
		/*this.mvc.perform(
			     post("/query")
			    .param("query", "DELETE FROM " + seriesInt32)
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$.success", is(false)));*/
	}
}
