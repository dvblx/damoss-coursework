package ru.melnikov.computershop.exception;

import java.util.NoSuchElementException;

public class PrinterNotFoundException extends NoSuchElementException {
    public PrinterNotFoundException(String printerId) {
        super(String.format("Принтер с Id %s не найден", printerId));
    }
}
