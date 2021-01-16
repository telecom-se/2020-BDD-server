package fr.tse.db.query.error;

import fr.tse.db.storage.exception.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.NoSuchElementException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        HashMap<String, Object> responseGlobal = new HashMap<>();
        responseGlobal.put("success", false);
        if(apiError != null) {
            responseGlobal.put("error", apiError);
        }
        return new ResponseEntity<>(responseGlobal, apiError.getStatus());
    }

    @ExceptionHandler(BadQueryException.class)
    protected ResponseEntity<Object> handleBadQuery(BadQueryException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), "SYNT_ERR");
        return buildResponseEntity(apiError);
    }
    
    @ExceptionHandler(SeriesAlreadyExistsException.class)
    protected ResponseEntity<Object> handleSeriesAlreadyExistsExceptionQuery() {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, "Series already exist", "S_NAME_EXISTS");
        return buildResponseEntity(apiError);
    }
    
   @ExceptionHandler(SeriesNotFoundException.class)
    protected ResponseEntity<Object> handleSeriesNotFoundExceptionQuery() {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Series doesn't exist", "S_NOT_FOUND");
        return buildResponseEntity(apiError);
    }
    
    @ExceptionHandler(WrongSeriesValueTypeException.class)
    protected ResponseEntity<Object> handleWrongSeriesValueTypeExceptionQuery() {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT,"Wrong Series Value Type", "S_TYPE_ERROR");
        return buildResponseEntity(apiError);
    }
    
    @ExceptionHandler(TimestampAlreadyExistsException.class)
    protected ResponseEntity<Object> handleTimestampAlreadyExistsExceptionQuery() {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT,"Timestamp Already Exists", "S_TIMESTAMP_EXISTS");
        return buildResponseEntity(apiError);
    }
    @ExceptionHandler(NoSuchElementException.class)
    protected  ResponseEntity<Object> handleEmptySeriesExceptionQuery(){
        ApiError apiError = new ApiError(HttpStatus.CONFLICT,"The series is empty.", "S_EMPTY");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleOthersException(){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,"Error not handle.", "S_OTHERS");
        return buildResponseEntity(apiError);
    }

}
