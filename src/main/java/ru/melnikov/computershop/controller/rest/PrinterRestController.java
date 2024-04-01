package ru.melnikov.computershop.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.melnikov.computershop.dto.PrinterDto;
import ru.melnikov.computershop.mapper.PrinterMapper;
import ru.melnikov.computershop.service.product.PrinterService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product/printer")
@RequiredArgsConstructor
public class PrinterRestController {

    private final PrinterService printerService;
    private final PrinterMapper printerMapper;

    @GetMapping
    public List<PrinterDto> getPrinters() {
        return printerMapper.toDtoList(
                printerService.getAll()
        );
    }

    @GetMapping("/{printerId}")
    public PrinterDto getPrinter(@PathVariable UUID printerId) {
        return printerMapper.toDto(
                printerService.getById(printerId)
        );
    }

    @PostMapping
    public PrinterDto createPrinter(@RequestBody PrinterDto printerDto) {
        return printerMapper.toDto(
                printerService.create(printerMapper.toEntity(printerDto))
        );
    }

    @PutMapping("/{printerId}")
    public PrinterDto updatePrinter(
            @PathVariable UUID printerId,
            @RequestBody PrinterDto printerDto
    ) {
        return printerMapper.toDto(
                printerService.update(printerId, printerMapper.toEntity(printerDto))
        );
    }

    @DeleteMapping("/{printerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrinter(@PathVariable UUID printerId){
        printerService.deleteById(printerId);
    }
}
