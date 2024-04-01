package ru.melnikov.computershop.exception;

import java.util.NoSuchElementException;

public class LaptopNotFoundException extends NoSuchElementException {
    public LaptopNotFoundException(String laptopId) {
        super(String.format("Ноутбук с Id %s не найден", laptopId));
    }
}
