package com.projeto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableZuulProxy
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

// Controlador para expor o endpoint de teste
@RestController
@RequestMapping("/gateway")
class ApiGatewayController {

    private final ApiGatewayService apiGatewayService;

    public ApiGatewayController(ApiGatewayService apiGatewayService) {
        this.apiGatewayService = apiGatewayService;
    }

    @GetMapping("/call/{serviceId}")
    public String callService(@PathVariable String serviceId) {
        return apiGatewayService.callExternalService(serviceId);
    }
}

// Serviço que faz chamada externa com Circuit Breaker
@Service
class ApiGatewayService {

    private final RestTemplate restTemplate;

    public ApiGatewayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "apiGatewayService", fallbackMethod = "fallbackResponse")
    public String callExternalService(String serviceId) {
        String url = "http://" + serviceId + "/some-endpoint";
        return restTemplate.getForObject(url, String.class);
    }

    public String fallbackResponse(String serviceId, Throwable throwable) {
        return "Serviço temporariamente indisponível. Tente novamente mais tarde.";
    }
}
