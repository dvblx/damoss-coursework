package ru.melnikov.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.melnikov.computershop.enumerate.ProductType;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private UUID id;
    private String modelName;
    private BigDecimal price;
    private ProductType productType;

    public String getUrl(){
        switch (productType){
            case PRINTER -> {
                return String.format("/product/printer/%s", id);
            }
            case LAPTOP -> {
                return String.format("/product/laptop/%s", id);
            }
            default -> {
                return String.format("/product/personal-computer/%s", id);
            }
        }
    }
}
