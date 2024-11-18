package com.projeto.controller;

import com.projeto.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/currency-conversion")
public class ConversionController {

    @Autowired
    private ConversionService conversionService;

    @GetMapping("/from/{from}/to/{to}/quantity/{quantity}")
    public String calculateConversion(@PathVariable String from, @PathVariable String to, @PathVariable double quantity) {
        return conversionService.getConversion(from, to, quantity);
    }
}
