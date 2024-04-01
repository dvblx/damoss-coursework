package ru.melnikov.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.melnikov.computershop.enumerate.PrinterType;
import ru.melnikov.computershop.model.product.ProductData;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrinterDto {
    private UUID id;
    private Boolean coloured;
    private PrinterType type;
    private ProductDto productData;
}
