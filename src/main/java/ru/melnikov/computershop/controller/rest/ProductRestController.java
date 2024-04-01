package ru.melnikov.computershop.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.melnikov.computershop.dto.ProductDto;
import ru.melnikov.computershop.mapper.ProductMapper;
import ru.melnikov.computershop.service.product.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductDto> getList(){
        return productMapper.toDtoList(productService.getAll());
    }
}
