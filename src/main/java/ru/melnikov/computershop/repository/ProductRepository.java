package ru.melnikov.computershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.melnikov.computershop.enumerate.ProductType;
import ru.melnikov.computershop.model.product.ProductData;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductData, UUID> {
    List<ProductData> findByProductType(ProductType productType);
}
