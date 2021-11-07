package ecommerce.service;

import ecommerce.kafka.consumer.KafkaService;
import ecommerce.model.PedidoCompra;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class DetectorFraudeService {

    public static void main(String[] args) {
        DetectorFraudeService detectorFraudeService = new DetectorFraudeService();

        try (KafkaService<PedidoCompra> kafkaService = new KafkaService<>(DetectorFraudeService.class.getSimpleName(),
                "LOJA_NOVO_PEDIDO", detectorFraudeService::parse, PedidoCompra.class, Map.of())) {
            kafkaService.run();
        }
    }

    private void parse(ConsumerRecord<String, PedidoCompra> record) {
        System.out.println("Processando validação de fraude");
        System.out.println(record.key() + " -- " + record.value() + " -- " + record.partition() + " -- " + record.offset());
    }
}
