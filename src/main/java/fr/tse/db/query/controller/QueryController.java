package fr.tse.db.query.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.tse.db.query.service.QueryService;

@RestController
public class QueryController {

    @Autowired
    QueryService queryService;

    @PostMapping(value = "/query",produces = "application/json")
    public ResponseEntity<?> postQuery(@RequestParam("query") String query) throws Exception {
        // Get object
        Object response = this.queryService.handleQuery(query);
        HashMap<String, Object> responseGlobal = new HashMap<>();
        responseGlobal.put("success", true);
        responseGlobal.put("data", response);
        return new ResponseEntity<>(responseGlobal, HttpStatus.OK);
    }
}
