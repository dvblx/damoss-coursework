package ru.melnikov.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaptopDto {
    private UUID id;
    private Float speed;
    private Integer ram;
    private Integer hd;
    private Float screen;
    private ProductDto productData;
}
