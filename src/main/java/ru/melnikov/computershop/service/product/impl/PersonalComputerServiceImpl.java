package ru.melnikov.computershop.service.product.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.melnikov.computershop.enumerate.ProductType;
import ru.melnikov.computershop.exception.PersonalComputerNotFoundException;
import ru.melnikov.computershop.model.product.PersonalComputer;
import ru.melnikov.computershop.repository.PersonalComputerRepository;
import ru.melnikov.computershop.service.product.PersonalComputerService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonalComputerServiceImpl implements PersonalComputerService {

    private static final String PC_DOES_NOT_EXISTS = "Персональный компьютер с id %s не найден";

    private final PersonalComputerRepository personalComputerRepository;

    @Override
    public List<PersonalComputer> getAll() {
        return personalComputerRepository.findAll();
    }

    @Override
    public PersonalComputer getById(UUID computerId) {
        return personalComputerRepository.findById(computerId)
                .orElseThrow(
                        () -> new PersonalComputerNotFoundException(computerId.toString())
                );
    }

    @Override
    @Transactional
    public PersonalComputer create(PersonalComputer personalComputer) {
        personalComputer.getProductData().setId(UUID.randomUUID());
        personalComputer.getProductData().setProductType(ProductType.PERSONAL_COMPUTER);
        return personalComputerRepository.save(personalComputer);
    }

    @Override
    @Transactional
    public PersonalComputer update(UUID computerId, PersonalComputer personalComputer) {

        return personalComputerRepository.save(personalComputerRepository.findById(computerId)
                .map(computerToUpdate -> {
                    computerToUpdate.getProductData().setModelName(personalComputer.getProductData().getModelName());
                    computerToUpdate.getProductData().setPrice(personalComputer.getProductData().getPrice());
                    computerToUpdate.setHd(personalComputer.getHd());
                    computerToUpdate.setRam(personalComputer.getRam());
                    computerToUpdate.setSpeed(personalComputer.getSpeed());
                    computerToUpdate.setCdType(personalComputer.getCdType());
                    return computerToUpdate;
                }).orElseThrow(() -> new PersonalComputerNotFoundException(String.format(PC_DOES_NOT_EXISTS, computerId))));
    }

    @Override
    public void deleteById(UUID computerId) {
        if (!personalComputerRepository.existsById(computerId)){
            throw new PersonalComputerNotFoundException(String.format(PC_DOES_NOT_EXISTS, computerId));
        }
        personalComputerRepository.deleteById(computerId);
    }
}
