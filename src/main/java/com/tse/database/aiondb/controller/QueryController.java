package com.tse.database.aiondb.controller;

import com.tse.database.aiondb.domain.Series;
import com.tse.database.aiondb.error.BadQueryException;
import com.tse.database.aiondb.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class QueryController {

    @Autowired
    QueryService queryService;

    @PostMapping(value = "/query",produces = "application/json")
    public ResponseEntity<?> postQuery(@RequestParam("query") String query) throws BadQueryException {
        // Get object
        List<Series> series = this.queryService.parseQuery(query);
        return new ResponseEntity<>(series, HttpStatus.OK);
    }
}
