package ru.melnikov.computershop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {PersonalComputerNotFoundException.class})
    public ErrorMessage notFoundException(PersonalComputerNotFoundException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {LaptopNotFoundException.class})
    public ErrorMessage notFoundException(NoSuchElementException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {PrinterNotFoundException.class})
    public ErrorMessage notFoundException(PrinterNotFoundException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ErrorMessage userAlreadyExistsException(UserAlreadyExistsException exception){
        return new ErrorMessage(exception.getMessage());
    }
}
