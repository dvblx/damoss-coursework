package ru.melnikov.computershop.exception;

import java.util.NoSuchElementException;

public class PersonalComputerNotFoundException extends NoSuchElementException {
    public PersonalComputerNotFoundException(String computerId) {
        super(String.format("Компьютер с Id %s не найден", computerId));
    }
}
