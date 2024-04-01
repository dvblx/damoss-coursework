package ru.melnikov.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.melnikov.computershop.enumerate.CdType;
import ru.melnikov.computershop.model.product.ProductData;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalComputerDto {
    private UUID id;
    private Float speed;
    private Integer ram;
    private Integer hd;
    private CdType cdType;
    private ProductDto productData;
}
