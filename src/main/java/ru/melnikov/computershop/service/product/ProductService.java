package ru.melnikov.computershop.service.product;

import ru.melnikov.computershop.enumerate.ProductType;
import ru.melnikov.computershop.model.product.ProductData;

import java.util.List;

public interface ProductService {
    List<ProductData> getAll();
    List<ProductData> filterByType(ProductType type);
}
