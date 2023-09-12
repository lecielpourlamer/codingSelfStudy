package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CoffeeApiController {

    @Autowired
    private CoffeeRepository coffeeRepository;

    // GET
    @GetMapping("/api/coffees")
    public Iterable<Coffee> index() {
        log.info("coffeeRepository.findAll() = {}", coffeeRepository.findAll());
        return coffeeRepository.findAll();
    }

    // Get one info
    @GetMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> show(@PathVariable Long id) {
        Coffee coffee = coffeeRepository.findById(id).orElse(null);
        return (coffee != null) ?
                ResponseEntity.status(HttpStatus.OK).body(coffee) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // Post - to create coffee
    @PostMapping("/api/coffees")
    public ResponseEntity<Coffee> create(@RequestBody CoffeeDto coffeeDto) {
        Coffee coffee = coffeeDto.toEntity();
        if (coffee.getId() != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
        Coffee created = coffeeRepository.save(coffee);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }



}
