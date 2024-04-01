package ru.melnikov.computershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.melnikov.computershop.model.product.Printer;

import java.util.UUID;

public interface PrinterRepository extends JpaRepository<Printer, UUID> {
}
