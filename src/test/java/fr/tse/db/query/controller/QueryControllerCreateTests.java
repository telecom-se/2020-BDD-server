package fr.tse.db.query.controller;

import fr.tse.db.query.error.BadQueryException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test_query_controller")
public class QueryControllerCreateTests {

    @Autowired
    protected MockMvc mvc;

    @Test
    public void testCreateController() throws Exception {

        mvc.perform(post("/query?query=CREATE MySeries int32"))
                .andExpect(status().is(200))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("success", is(true)))
        ;
    }

    @Test
    public void testCreateControllerSeriesAlreadyExistsExceptionTest() throws Exception {

        mvc.perform(post("/query?query=CREATE seriesint32 int32"))
                .andExpect(status().is(409))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("success", is(false)))
                .andExpect(jsonPath("error.code", is("S_NAME_EXISTS")))
                .andExpect(jsonPath("error.message", is("Series already exist")))

        ;
    }

    @Test
    public void testCreateControllerTypeNotProvidedExceptionTest() throws Exception {
        mvc.perform(post("/query?query=CREATE MySeries3"))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("success", is(false)))
                .andExpect(jsonPath("error.code", is("SYNT_ERR")))
                .andExpect(jsonPath("error.message", is(BadQueryException.ERROR_MESSAGE_CREATE_GENERAL)))
        ;
    }

    @Test
    public void testCreateControllerTypeNotExistsExceptionTest() throws Exception {
        mvc.perform(post("/query?query=CREATE MySeries2 int10"))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("success", is(false)))
                .andExpect(jsonPath("error.code", is("SYNT_ERR")))
                .andExpect(jsonPath("error.message", is(BadQueryException.ERROR_MESSAGE_CREATE_IN_TYPE)))
        ;
    }

    @Test
    public void testCreateControllerSpecialCharactersExceptionTest() throws Exception {

        mvc.perform(post("/query?query=CREATE MySeries3@ int32"))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("success", is(false)))
                .andExpect(jsonPath("error.code", is("SYNT_ERR")))
                .andExpect(jsonPath("error.message", is(BadQueryException.ERROR_MESSAGE_CREATE_IN_NAME_SPECIAL_CHARACTERS)))
        ;

        mvc.perform(post("/query?query=CREATE MySeries3& int32"))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("success", is(false)))
                .andExpect(jsonPath("error.code", is("SYNT_ERR")))
                .andExpect(jsonPath("error.message", is(BadQueryException.ERROR_MESSAGE_CREATE_GENERAL)))
        ;
    }

}
