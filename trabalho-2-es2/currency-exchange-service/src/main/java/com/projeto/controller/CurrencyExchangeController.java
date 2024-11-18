package com.projeto.controller;

import com.projeto.service.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/currency-exchange")
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeService exchangeService;

    @GetMapping("/from/{from}/to/{to}")
    public String getExchangeRate(@PathVariable String from, @PathVariable String to) {
        return exchangeService.getExchangeRate(from, to);
    }
}
