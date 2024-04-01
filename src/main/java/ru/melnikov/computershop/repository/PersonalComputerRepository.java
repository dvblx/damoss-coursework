package ru.melnikov.computershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.melnikov.computershop.model.product.PersonalComputer;

import java.util.UUID;

public interface PersonalComputerRepository extends JpaRepository<PersonalComputer, UUID> {
}
