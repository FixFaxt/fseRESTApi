package main.fseRESTApi.api;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex,
      HttpServletRequest request) {
    Map<String, Object> body = new HashMap<>();
    body.put("status", ex.getStatusCode().value());
    body.put("error", ex.getStatusCode().toString());
    body.put("message", ex.getReason());
    body.put("timestamp", LocalDateTime.now());
    body.put("path", request.getRequestURI());

    return new ResponseEntity<>(body, ex.getStatusCode());
  }
}