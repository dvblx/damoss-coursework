package ru.melnikov.computershop.service.product;

import ru.melnikov.computershop.model.product.Laptop;

import java.util.List;
import java.util.UUID;

public interface LaptopService {
    List<Laptop> getAll();
    Laptop getById(UUID laptopId);
    Laptop create(Laptop laptop);
    Laptop update(UUID laptopId, Laptop laptop);
    void deleteById(UUID laptopId);
}
