package ru.melnikov.computershop.mapper;

import org.mapstruct.Mapper;
import ru.melnikov.computershop.dto.ProductDto;
import ru.melnikov.computershop.model.product.ProductData;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductData.class})
public interface ProductMapper {
    ProductData toEntity(ProductDto productDto);
    ProductDto toDto(ProductData productData);
    List<ProductData> toEntityList(List<ProductDto> productDtoList);
    List<ProductDto> toDtoList(List<ProductData> productDataList);
}
