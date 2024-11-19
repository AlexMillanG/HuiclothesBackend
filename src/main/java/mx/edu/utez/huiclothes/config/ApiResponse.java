package mx.edu.utez.huiclothes.config;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse {
    private String message;
    private boolean error;
    private HttpStatus httpStatus;
    private Object object;
    private Long userId;

    public ApiResponse(Object object, HttpStatus httpStatus) {
        this.object = object;
        this.httpStatus = httpStatus;
    }

    public ApiResponse(String message, boolean error, HttpStatus httpStatus, Object object) {
        this.message = message;
        this.error = error;
        this.httpStatus = httpStatus;
        this.object = object;
    }

    public ApiResponse(String message, boolean error, HttpStatus httpStatus, Object object,Long userId) {
        this.message = message;
        this.error = error;
        this.httpStatus = httpStatus;
        this.object = object;
        this.userId = userId;
    }



}
