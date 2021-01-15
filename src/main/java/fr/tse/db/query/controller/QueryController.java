package fr.tse.db.query.controller;

import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.tse.db.query.service.QueryService;

@Slf4j
@RestController
public class QueryController {

    @Autowired
    QueryService queryService;

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/query", produces = "application/json")
    public ResponseEntity<?> postQuery(@RequestParam("query") String query) throws Exception {
       log.debug(query);
        // Get object
        HashMap<String, Object> response = this.queryService.handleQuery(query);
        HashMap<String, Object> responseGlobal = new HashMap<>();
        responseGlobal.put("success", true);
        if(response != null && response.size() > 0) {
            responseGlobal.put("data", response);
        }
        return new ResponseEntity<>(responseGlobal, HttpStatus.OK);
    }
}
