package ecommerce.service;

import ecommerce.kafka.consumer.KafkaService;
import ecommerce.model.Email;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class EnvioEmailService {

    public static void main(String[] args) {
        EnvioEmailService envioEmailService = new EnvioEmailService();

        try (KafkaService<Email> kafkaService = new KafkaService<>(EnvioEmailService.class.getSimpleName(),
                "LOJA_ENVIA_EMAIL", envioEmailService::parse, Email.class, Map.of())) {
            kafkaService.run();
        }
    }

    private void parse(ConsumerRecord<String, Email> record) {
        System.out.println("Processando email");
        System.out.println(record.key() + " -- " + record.value() + " -- " + record.partition() + " -- " + record.offset());
    }
}
