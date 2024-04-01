package ru.melnikov.computershop.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.melnikov.computershop.dto.LaptopDto;
import ru.melnikov.computershop.mapper.LaptopMapper;
import ru.melnikov.computershop.service.product.LaptopService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product/laptop")
@RequiredArgsConstructor
public class LaptopRestController {

    private final LaptopService laptopService;
    private final LaptopMapper laptopMapper;

    @GetMapping
    public List<LaptopDto> getLaptops() {
        return laptopMapper.toDtoList(laptopService.getAll());
    }

    @GetMapping("/{laptopId}")
    public LaptopDto getLaptop(@PathVariable UUID laptopId) {
        return laptopMapper.toDto(laptopService.getById(laptopId));
    }

    @PostMapping
    public LaptopDto createLaptop(@RequestBody LaptopDto laptopDto) {
        return laptopMapper.toDto(
                laptopService.create(laptopMapper.toEntity(laptopDto))
        );
    }

    @PutMapping("/{laptopId}")
    public LaptopDto updateLaptop(
            @PathVariable UUID laptopId,
            @RequestBody LaptopDto laptopDto
    ) {
        return laptopMapper.toDto(
                laptopService.update(laptopId, laptopMapper.toEntity(laptopDto))
        );
    }

    @DeleteMapping("/{laptopId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLaptop(@PathVariable UUID laptopId){
        laptopService.deleteById(laptopId);
    }
}
