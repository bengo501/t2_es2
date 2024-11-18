package com.projeto;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConsumer {

    // Método que escuta mensagens na fila definida no arquivo de configuração (application.yml)
    @RabbitListener(queues = "${queue.name}")
    public void receiveConversionRequest(String message) {
        System.out.println("Mensagem recebida do RabbitMQ: " + message);
    }
}
