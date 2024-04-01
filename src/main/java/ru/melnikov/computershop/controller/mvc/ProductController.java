package ru.melnikov.computershop.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.melnikov.computershop.mapper.ProductMapper;
import ru.melnikov.computershop.service.product.ProductService;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private static String PRODUCTS_PAGE = "product/product";
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public String getProducts(Model model) {
        model.addAttribute(
                "products", productMapper.toDtoList(productService.getAll())
        );
        return PRODUCTS_PAGE;
    }
}
