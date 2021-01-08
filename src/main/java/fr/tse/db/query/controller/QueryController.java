package fr.tse.db.query.controller;

import fr.tse.db.query.domain.Series;
import fr.tse.db.query.error.BadQueryException;
import fr.tse.db.query.error.SeriesAlreadyExistsQueryException;
import fr.tse.db.query.service.QueryService;
import fr.tse.db.storage.exception.SeriesAlreadyExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QueryController {

    @Autowired
    QueryService queryService;

    @PostMapping(value = "/query",produces = "application/json")
    public ResponseEntity<?> postQuery(@RequestParam("query") String query) throws BadQueryException, SeriesAlreadyExistsQueryException {
        // Get object
        List<Series> series = this.queryService.handleQuery(query);
        return new ResponseEntity<>(series, HttpStatus.OK);
    }
}
