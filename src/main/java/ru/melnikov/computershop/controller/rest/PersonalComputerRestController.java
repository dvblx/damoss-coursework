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
import ru.melnikov.computershop.dto.PersonalComputerDto;
import ru.melnikov.computershop.mapper.PersonalComputerMapper;
import ru.melnikov.computershop.service.product.PersonalComputerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product/personal-computer")
@RequiredArgsConstructor
public class PersonalComputerRestController {

    private final PersonalComputerService personalComputerService;
    private final PersonalComputerMapper personalComputerMapper;

    @GetMapping
    public List<PersonalComputerDto> getComputers() {
        return personalComputerMapper.toDtoList(personalComputerService.getAll());
    }

    @GetMapping("/{computerId}")
    public PersonalComputerDto getComputer(@PathVariable UUID computerId) {
        return personalComputerMapper.toDto(personalComputerService.getById(computerId));
    }

    @PostMapping
    public PersonalComputerDto createComputer(@RequestBody PersonalComputerDto personalComputerDto) {
        return personalComputerMapper.toDto(
                personalComputerService.create(personalComputerMapper.toEntity(personalComputerDto))
        );
    }

    @PutMapping("/{computerId}")
    public PersonalComputerDto updateComputer(
            @PathVariable UUID computerId,
            @RequestBody PersonalComputerDto personalComputerDto
    ) {
        return personalComputerMapper.toDto(
                personalComputerService.update(
                        computerId,
                        personalComputerMapper.toEntity(personalComputerDto)
                )
        );
    }

    @DeleteMapping("/{computerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComputer(@PathVariable UUID computerId){
        personalComputerService.deleteById(computerId);
    }
}
