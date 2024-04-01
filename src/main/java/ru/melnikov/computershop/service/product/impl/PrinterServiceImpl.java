package ru.melnikov.computershop.service.product.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.melnikov.computershop.exception.PrinterNotFoundException;
import ru.melnikov.computershop.model.product.Printer;
import ru.melnikov.computershop.repository.PrinterRepository;
import ru.melnikov.computershop.service.product.PrinterService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrinterServiceImpl implements PrinterService {

    private static final String PRINTER_DOES_NOT_EXISTS = "Принтер с id %s не найден";

    private final PrinterRepository printerRepository;

    @Override
    public List<Printer> getAll() {
        return printerRepository.findAll();
    }

    @Override
    public Printer getById(UUID printerId) {
        return printerRepository.findById(printerId)
                .orElseThrow(
                        () -> new PrinterNotFoundException(printerId.toString())
                );
    }

    @Override
    @Transactional
    public Printer create(Printer printer) {
        return printerRepository.save(printer);
    }

    @Override
    public Printer update(UUID printerId, Printer printer) {
        if (!printerRepository.existsById(printerId)){
            throw new PrinterNotFoundException(String.format(PRINTER_DOES_NOT_EXISTS, printerId));
        }
        return printerRepository.save(printer);
    }

    @Override
    public void deleteById(UUID printerId) {
        if (!printerRepository.existsById(printerId)){
            throw new PrinterNotFoundException(String.format(PRINTER_DOES_NOT_EXISTS, printerId));
        }
        printerRepository.deleteById(printerId);
    }
}
