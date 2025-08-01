package com.huysor.saas.common.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiRes<T> {
    private String message;
    private Integer statusCode;
    private T data;


    public static <T> ApiRes<T> success(String message, T data) {
        return of(message, 200, data);
    }

    public static <T> ApiRes<T> success(String message, T data, Integer statusCode) {
        return of(message, statusCode, data);
    }

    public static <T> ApiRes<T> success(T data) {
        return of("Success", 200, data);
    }

    public static <T> ApiRes<T> created() {
        return of("Successfully created", 201, null);
    }

    public static <T> ApiRes<T> error(String message, T data) {
        return of(message, 500, data);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static <T> ApiRes<T> notFound(String message) {
        return of(message, 404, null);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static <T> ApiRes<T> notFound() {
        return of("Not found", 404, null);
    }

    public static <T> ApiRes<T> error(String message, T data, Integer statusCode) {
        return of(message, statusCode, data);
    }

    public static <T> ApiRes<T> error(String message) {
        return of(message, 500, null);
    }

    public static <T> ApiRes<T> error(String message, Integer statusCode) {
        return of(message, statusCode, null);

    }
}

