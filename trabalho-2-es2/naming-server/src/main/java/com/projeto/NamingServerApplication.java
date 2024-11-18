package com.projeto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaServer
public class NamingServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(NamingServerApplication.class, args);
    }

    // Bean RestTemplate para uso em chamadas externas
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

// Controlador para testar o serviço externo com Circuit Breaker
@RestController
@RequestMapping("/external")
class ExternalController {

    private final ExternalService externalService;

    public ExternalController(ExternalService externalService) {
        this.externalService = externalService;
    }

    @GetMapping("/call/{url}")
    public String makeExternalCall(@PathVariable String url) {
        return externalService.makeExternalCall(url);
    }
}

// Serviço que usa Circuit Breaker para chamadas externas
@Service
class ExternalService {

    private final RestTemplate restTemplate;

    public ExternalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "externalService", fallbackMethod = "fallbackResponse")
    public String makeExternalCall(String url) {
        String fullUrl = "http://" + url;
        return restTemplate.getForObject(fullUrl, String.class);
    }

    public String fallbackResponse(String url, Throwable exception) {
        return "Serviço externo temporariamente indisponível.";
    }
}
