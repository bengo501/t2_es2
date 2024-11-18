package com.projeto.service;

import com.projeto.entity.ConversionRate;
import com.projeto.repository.ConversionRateRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {

    @Autowired
    private ConversionRateRepository conversionRateRepository;

    @CircuitBreaker(name = "conversionService", fallbackMethod = "fallbackConversion")
    public String getConversion(String from, String to, double quantity) {
        ConversionRate rate = conversionRateRepository.findByCurrencyFromAndCurrencyTo(from, to);
        if (rate != null) {
            double convertedAmount = quantity * rate.getRate();
            return "Conversão de " + quantity + " " + from + " para " + to + ": " + convertedAmount;
        } else {
            return "Taxa de câmbio não encontrada.";
        }
    }

    public String fallbackConversion(String from, String to, double quantity, Throwable exception) {
        return "Serviço de conversão temporariamente indisponível. Tente novamente mais tarde.";
    }
}
