package com.projeto.service;

import com.projeto.entity.ExchangeRate;
import com.projeto.repository.ExchangeRateRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyExchangeService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @CircuitBreaker(name = "currencyExchangeService", fallbackMethod = "fallbackGetExchangeRate")
    public String getExchangeRate(String from, String to) {
        ExchangeRate rate = exchangeRateRepository.findByCurrencyFromAndCurrencyTo(from, to);
        return (rate != null) ? rate.getRate().toString() : "Taxa de câmbio não encontrada.";
    }

    public String fallbackGetExchangeRate(String from, String to, Throwable exception) {
        return "Serviço temporariamente indisponível.";
    }
}
