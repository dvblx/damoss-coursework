package ru.melnikov.computershop.mapper;

import org.mapstruct.Mapper;
import ru.melnikov.computershop.dto.PersonalComputerDto;
import ru.melnikov.computershop.dto.ProductDto;
import ru.melnikov.computershop.model.product.PersonalComputer;
import ru.melnikov.computershop.model.product.ProductData;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PersonalComputer.class, ProductData.class})
public interface PersonalComputerMapper {
    PersonalComputer toEntity(PersonalComputerDto personalComputerDto);
    PersonalComputerDto toDto(PersonalComputer personalComputer);
    List<PersonalComputer> toEntityList(List<PersonalComputerDto> personalComputerDtoList);
    List<PersonalComputerDto> toDtoList(List<PersonalComputer> personalComputerList);
}
