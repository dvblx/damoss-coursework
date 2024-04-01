package ru.melnikov.computershop.mapper;

import org.mapstruct.Mapper;
import ru.melnikov.computershop.dto.LaptopDto;
import ru.melnikov.computershop.dto.ProductDto;
import ru.melnikov.computershop.model.product.Laptop;
import ru.melnikov.computershop.model.product.ProductData;

import java.util.List;

@Mapper(componentModel = "spring", uses = {Laptop.class, ProductData.class})
public interface LaptopMapper {
    Laptop toEntity(LaptopDto laptopDto);
    LaptopDto toDto(Laptop laptop);
    List<Laptop> toEntityList(List<LaptopDto> laptopDtoList);
    List<LaptopDto> toDtoList(List<Laptop> laptopList);
}
