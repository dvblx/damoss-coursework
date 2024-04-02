package ru.melnikov.computershop.controller.mvc;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.melnikov.computershop.dto.LaptopDto;
import ru.melnikov.computershop.dto.ProductDto;
import ru.melnikov.computershop.enumerate.ProductType;
import ru.melnikov.computershop.exception.LaptopNotFoundException;
import ru.melnikov.computershop.mapper.LaptopMapper;
import ru.melnikov.computershop.mapper.ProductMapper;
import ru.melnikov.computershop.service.product.LaptopService;
import ru.melnikov.computershop.service.product.ProductService;

import java.util.UUID;

@Controller
@RequestMapping("/product/laptop")
@RequiredArgsConstructor
public class LaptopController {

    private static final String REDIRECT_TO_LIST = "redirect:/product/laptop";
    private static final String PAGE_404 = "base/notfound";
    private static final String LAPTOPS_PAGE = "product/laptop/laptop";
    private static final String LAPTOP_EDIT_PAGE = "product/laptop/editlaptop";
    private final LaptopService laptopService;
    private final LaptopMapper laptopMapper;

    @GetMapping
    public String getLaptops(Model model) {
        model.addAttribute("laptop", LaptopDto.builder()
                .productData(ProductDto.builder().build())
                .build());
        model.addAttribute("products", laptopMapper.toDtoList(
                laptopService.getAll()
        ));
        return LAPTOPS_PAGE;
    }

    @GetMapping("/{laptopId}")
    public String editLaptop(Model model, @PathVariable("laptopId") UUID laptopId) {
        try {
            model.addAttribute("laptop", laptopService.getById(laptopId));
            model.addAttribute("products", laptopMapper.toDtoList(
                    laptopService.getAll()
            ));
            return LAPTOP_EDIT_PAGE;
        } catch (LaptopNotFoundException exception) {
            return PAGE_404;
        }

    }

    @PostMapping
    public String createLaptop(Model model, @Valid LaptopDto laptop, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("laptopEdit", laptop);
            model.addAttribute("products", laptopMapper.toDtoList(
                    laptopService.getAll()
            ));
            return LAPTOP_EDIT_PAGE;
        }
        laptopService.create(laptopMapper.toEntity(laptop));
        return REDIRECT_TO_LIST;
    }

    @PostMapping("/{laptopId}")
    public String updateLaptop(
            @PathVariable("laptopId") UUID laptopId,
            Model model,
            LaptopDto laptop,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            model.addAttribute("laptopEdit", laptop);
            model.addAttribute("products", laptopMapper.toDtoList(
                    laptopService.getAll()
            ));
            return LAPTOP_EDIT_PAGE;
        }
        laptopService.update(laptopId, laptopMapper.toEntity(laptop));
        return REDIRECT_TO_LIST;
    }

    @GetMapping("/{laptopId}/delete")
    public String delete(@PathVariable("laptopId") UUID laptopId) {
        try{
            laptopService.deleteById(laptopId);
            return REDIRECT_TO_LIST;
        } catch (LaptopNotFoundException exception) {
            return PAGE_404;
        }

    }
}
