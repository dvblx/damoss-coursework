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
import ru.melnikov.computershop.dto.PrinterDto;
import ru.melnikov.computershop.enumerate.PrinterType;
import ru.melnikov.computershop.enumerate.ProductType;
import ru.melnikov.computershop.exception.PrinterNotFoundException;
import ru.melnikov.computershop.mapper.PrinterMapper;
import ru.melnikov.computershop.mapper.ProductMapper;
import ru.melnikov.computershop.service.product.PrinterService;
import ru.melnikov.computershop.service.product.ProductService;

import java.util.UUID;

@Controller
@RequestMapping("/product/printer")
@RequiredArgsConstructor
public class PrinterController {

    private static final String REDIRECT_TO_LIST = "redirect:/product/printer";
    private static final String PAGE_404 = "base/notfound";
    private static final String PRINTERS_PAGE = "product/printer/printer";
    private static final String PRINTER_EDIT_PAGE = "product/printer/editprinter";

    private final ProductService productService;
    private final PrinterService printerService;
    private final PrinterMapper printerMapper;
    private final ProductMapper productMapper;

    @GetMapping
    public String getPrinters(Model model, PrinterDto printer) {
        model.addAttribute("printer", printer);
        model.addAttribute("products", productMapper.toDtoList(
                productService.filterByType(ProductType.PRINTER)
        ));
        model.addAttribute("printerTypes", PrinterType.values());
        return PRINTERS_PAGE;
    }

    @GetMapping("/{printerId}")
    public String editPrinter(Model model, @PathVariable("printerId") UUID printerId) {
        try {
            model.addAttribute("printerEdit", printerService.getById(printerId));
            model.addAttribute("products", productMapper.toDtoList(
                    productService.filterByType(ProductType.PRINTER)
            ));
            model.addAttribute("printerTypes", PrinterType.values());
            return PRINTER_EDIT_PAGE;
        } catch (PrinterNotFoundException exception) {
            return PAGE_404;
        }

    }

    @PostMapping
    public String createPrinter(Model model, @Valid PrinterDto printer, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("printerEdit", printer);
            model.addAttribute("products", productMapper.toDtoList(
                    productService.filterByType(ProductType.PRINTER)
            ));
            model.addAttribute("printerTypes", PrinterType.values());
            return PRINTER_EDIT_PAGE;
        }
        printerService.create(printerMapper.toEntity(printer));
        return REDIRECT_TO_LIST;
    }

    @PostMapping("/{printerId}")
    public String updatePrinter(
            @PathVariable("printerId") UUID printerId,
            Model model,
            PrinterDto printer,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            model.addAttribute("printerEdit", printer);
            model.addAttribute("products", productMapper.toDtoList(
                    productService.filterByType(ProductType.PRINTER)
            ));
            model.addAttribute("printerTypes", PrinterType.values());
            return PRINTER_EDIT_PAGE;
        }
        printerService.update(printerId, printerMapper.toEntity(printer));
        return REDIRECT_TO_LIST;
    }

    @GetMapping("/{printerId}/delete")
    public String delete(@PathVariable("printerId") UUID printerId) {
        try{
            printerService.deleteById(printerId);
            return REDIRECT_TO_LIST;
        } catch (PrinterNotFoundException exception) {
            return PAGE_404;
        }

    }
}
