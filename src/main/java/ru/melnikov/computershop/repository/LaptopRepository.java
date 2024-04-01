package ru.melnikov.computershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.melnikov.computershop.model.product.Laptop;

import java.util.UUID;

public interface LaptopRepository extends JpaRepository<Laptop, UUID> {
}
