package fr.tse.db.query.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
class QueryControllerDeleteTests {
	
	@Autowired
	private MockMvc mvc;
	
	private String seriesInt32 = "seriesInt32";
	
	private Requests request = new RequestsImpl();
	
	@BeforeEach
	public void initControllerTest() throws Exception {	
		this.mvc.perform( 
			     post("/query")
			    .param("query", "CREATE " + seriesInt32 + " int32")
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$.success", is(true)));
		
		this.mvc.perform( 
			     post("/query")
			    .param("query", "INSERT INTO " + seriesInt32 + " VALUES ((300001,10), (300002,10));")
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$.success", is(true)));
	}
	
	@Test
	public void testQueryControllerDeleteTests() throws Exception {
		/*
		this.mvc.perform( 
			     post("/query")
			    .param("query", "DELETE FROM " + seriesInt32 + " WHERE TIMESTAMP == 300001")
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$.success", is(true)));
		
		this.mvc.perform( 
			     post("/query")
			    .param("query", "DELETE FROM " + seriesInt32 + " WHERE TIMESTAMP == 300001")
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$.success", is(false)));
		*/
		
		
		this.mvc.perform( 
			     post("/query")
			    .param("query", "DELETE FROM " + seriesInt32)
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$.success", is(true)));
		
		/*
		this.mvc.perform( 
			     post("/query")
			    .param("query", "DELETE FROM " + seriesInt32)
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$.success", is(false)));
		 */
	}
}
