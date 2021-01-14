package fr.tse.db.query.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

@Data
class ApiError {

    private HttpStatus status;
    private HashMap<String, Object> error = new HashMap<>();
    private String success = "false";

    private ApiError() {
        error.put("timestamp", LocalDateTime.now());
    }

    ApiError(HttpStatus status, String message, String code) {
        this();
        this.error.put("status", status);
        this.error.put("message", message);
        this.error.put("code", code);
    }
}
