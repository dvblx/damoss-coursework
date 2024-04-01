package ru.melnikov.computershop.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.melnikov.computershop.enumerate.ProductType;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "computer_shop_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductData{
    @Id
    private UUID id;
    @Column(name = "model")
    private String modelName;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ProductType productType;
}
