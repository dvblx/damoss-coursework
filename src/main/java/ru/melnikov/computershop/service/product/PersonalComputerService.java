package ru.melnikov.computershop.service.product;

import ru.melnikov.computershop.model.product.PersonalComputer;

import java.util.List;
import java.util.UUID;

public interface PersonalComputerService {
    List<PersonalComputer> getAll();
    PersonalComputer getById(UUID computerId);
    PersonalComputer create(PersonalComputer personalComputer);
    PersonalComputer update(UUID computerId, PersonalComputer personalComputer);
    void deleteById(UUID computerId);
}
