package fr.tse.db.query.error;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fr.tse.db.storage.exception.SeriesAlreadyExistsException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(BadQueryException.class)
    protected ResponseEntity<Object> handleBadQuery(BadQueryException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), "SYNT_ERR");
        return buildResponseEntity(apiError);
    }
    
    @ExceptionHandler(SeriesAlreadyExistsQueryException.class)
    protected ResponseEntity<Object> handleSeriesAlreadyExistsQuery(SeriesAlreadyExistsQueryException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), "S_NAME_EXISTS");
        return buildResponseEntity(apiError);
    }
}
