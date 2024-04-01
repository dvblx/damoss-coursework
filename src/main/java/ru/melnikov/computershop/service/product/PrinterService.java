package ru.melnikov.computershop.service.product;

import ru.melnikov.computershop.model.product.Printer;

import java.util.List;
import java.util.UUID;

public interface PrinterService {
    List<Printer> getAll();
    Printer getById(UUID printerId);
    Printer create(Printer printer);
    Printer update(UUID printerId, Printer printer);
    void deleteById(UUID printerId);
}
