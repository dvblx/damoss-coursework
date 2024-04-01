package ru.melnikov.computershop.mapper;

import org.mapstruct.Mapper;
import ru.melnikov.computershop.dto.PrinterDto;
import ru.melnikov.computershop.dto.ProductDto;
import ru.melnikov.computershop.model.product.Printer;
import ru.melnikov.computershop.model.product.ProductData;

import java.util.List;

@Mapper(componentModel = "spring", uses = {Printer.class, ProductData.class})
public interface PrinterMapper {
    Printer toEntity(PrinterDto printerDto);
    PrinterDto toDto(Printer printer);
    List<Printer> toEntityList(List<PrinterDto> printerDtoList);
    List<PrinterDto> toDtoList(List<Printer> printerList);
}
