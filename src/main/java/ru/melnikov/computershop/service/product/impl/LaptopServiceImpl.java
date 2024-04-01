package ru.melnikov.computershop.service.product.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.melnikov.computershop.exception.LaptopNotFoundException;
import ru.melnikov.computershop.model.product.Laptop;
import ru.melnikov.computershop.repository.LaptopRepository;
import ru.melnikov.computershop.service.product.LaptopService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LaptopServiceImpl implements LaptopService {

    private static final String LAPTOP_DOES_NOT_EXISTS = "Ноутбук с id %s не найден";

    private final LaptopRepository laptopRepository;

    @Override
    public List<Laptop> getAll() {
        return laptopRepository.findAll();
    }

    @Override
    public Laptop getById(UUID laptopId) {
        return laptopRepository.findById(laptopId).orElseThrow(
                () -> new LaptopNotFoundException(laptopId.toString())
        );
    }

    @Override
    @Transactional
    public Laptop create(Laptop laptop) {
        return laptopRepository.save(laptop);
    }

    @Override
    public Laptop update(UUID laptopId, Laptop laptop) {
        if (!laptopRepository.existsById(laptopId)){
            throw new LaptopNotFoundException(String.format(LAPTOP_DOES_NOT_EXISTS, laptopId));
        }
        return laptopRepository.save(laptop);
    }


    @Override
    public void deleteById(UUID laptopId) {
        if (!laptopRepository.existsById(laptopId)){
            throw new LaptopNotFoundException(String.format(LAPTOP_DOES_NOT_EXISTS, laptopId));
        }
        laptopRepository.deleteById(laptopId);
    }
}
