package ru.melnikov.computershop.service.product.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.melnikov.computershop.enumerate.ProductType;
import ru.melnikov.computershop.model.product.ProductData;
import ru.melnikov.computershop.repository.ProductRepository;
import ru.melnikov.computershop.service.product.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductData> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductData> filterByType(ProductType type) {
        return productRepository.findByProductType(type);
    }
}
