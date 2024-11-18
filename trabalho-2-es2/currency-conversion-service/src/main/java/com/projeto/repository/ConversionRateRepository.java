package com.projeto.repository;

import com.projeto.entity.ConversionRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionRateRepository extends JpaRepository<ConversionRate, Long> {
    ConversionRate findByCurrencyFromAndCurrencyTo(String from, String to);
}
