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
import ru.melnikov.computershop.dto.PersonalComputerDto;
import ru.melnikov.computershop.dto.ProductDto;
import ru.melnikov.computershop.enumerate.CdType;
import ru.melnikov.computershop.enumerate.ProductType;
import ru.melnikov.computershop.exception.PersonalComputerNotFoundException;
import ru.melnikov.computershop.mapper.PersonalComputerMapper;
import ru.melnikov.computershop.mapper.ProductMapper;
import ru.melnikov.computershop.service.product.PersonalComputerService;
import ru.melnikov.computershop.service.product.ProductService;

import java.util.UUID;

@Controller
@RequestMapping("/product/personal-computer")
@RequiredArgsConstructor
public class PersonalComputerController {

    private static final String REDIRECT_TO_LIST = "redirect:/product/personal-computer";
    private static final String PAGE_404 = "base/notfound";
    private static final String COMPUTERS_PAGE = "product/computer/computer";
    private static final String COMPUTER_EDIT_PAGE = "product/computer/editcomputer";
    private final PersonalComputerService computerService;
    private final PersonalComputerMapper computerMapper;

    @GetMapping
    public String getComputers(Model model) {
        model.addAttribute("computer", PersonalComputerDto.builder()
                .productData(ProductDto.builder().build())
                .build());
        model.addAttribute("products", computerMapper.toDtoList(
                computerService.getAll()
        ));
        model.addAttribute("diskTypes", CdType.values());
        return COMPUTERS_PAGE;
    }

    @GetMapping("/{computerId}")
    public String editComputer(Model model, @PathVariable("computerId") UUID computerId) {
        try {
            model.addAttribute("computer", computerService.getById(computerId));
            model.addAttribute("products", computerMapper.toDtoList(
                    computerService.getAll()
            ));
            model.addAttribute("diskTypes", CdType.values());
            return COMPUTER_EDIT_PAGE;
        } catch (PersonalComputerNotFoundException exception) {
            return PAGE_404;
        }

    }

    @PostMapping
    public String createComputer(Model model, @Valid PersonalComputerDto computer, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("computer", computer);
            model.addAttribute("products", computerMapper.toDtoList(
                    computerService.getAll()
            ));
            model.addAttribute("diskTypes", CdType.values());
            return COMPUTER_EDIT_PAGE;
        }
        computerService.create(computerMapper.toEntity(computer));
        return REDIRECT_TO_LIST;
    }

    @PostMapping("/{computerId}")
    public String updateComputer(
            @PathVariable("computerId") UUID computerId,
            Model model,
            PersonalComputerDto computer,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            model.addAttribute("computer", computer);
            model.addAttribute("products", computerMapper.toDtoList(
                    computerService.getAll()
            ));
            return COMPUTER_EDIT_PAGE;
        }
        computerService.update(computerId, computerMapper.toEntity(computer));
        return REDIRECT_TO_LIST;
    }

    @GetMapping("/{computerId}/delete")
    public String delete(@PathVariable("computerId") UUID computerId) {
        try{
            computerService.deleteById(computerId);
            return REDIRECT_TO_LIST;
        } catch (PersonalComputerNotFoundException exception) {
            return PAGE_404;
        }

    }
}
