package com.andrewn.springMVC.config;

import com.andrewn.springMVC.exceptions.TodoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TodoControllerAdvice {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  @ExceptionHandler(TodoNotFoundException.class)
  String todoNotFoundHandler(TodoNotFoundException ex) {
    return ex.getMessage();
  }
}
