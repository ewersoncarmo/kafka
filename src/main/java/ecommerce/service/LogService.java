package ecommerce.service;

import ecommerce.kafka.consumer.KafkaService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Map;
import java.util.regex.Pattern;

public class LogService {

    public static void main(String[] args) {
        LogService logService = new LogService();

        try (KafkaService<String> kafkaService = new KafkaService<>(LogService.class.getSimpleName(),
                Pattern.compile("LOJA_.*"), logService::parse, String.class,
                Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))) {
            kafkaService.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("Log");
        System.out.println(record.key() + " -- " + record.value() + " -- " + record.partition() + " -- " + record.offset());
    }
}
