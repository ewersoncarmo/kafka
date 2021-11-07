package ecommerce;

import ecommerce.kafka.producer.KafkaDispatcher;
import ecommerce.model.Email;
import ecommerce.model.PedidoCompra;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Pedido {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (KafkaDispatcher pedidoCompraDispatcher = new KafkaDispatcher<PedidoCompra>()) {
            try (KafkaDispatcher emailDispatcher = new KafkaDispatcher<Email>()) {
                Pedido pedido = new Pedido();
                pedido.pedidoCompra(pedidoCompraDispatcher);
                pedido.email(emailDispatcher);
            }
        }
    }

    private void pedidoCompra(KafkaDispatcher pedidoCompraDispatcher) throws ExecutionException, InterruptedException {
        String usuarioId = UUID.randomUUID().toString();
        String pedidoId = UUID.randomUUID().toString();
        BigDecimal valor = BigDecimal.valueOf(100);

        PedidoCompra pedidoCompra = new PedidoCompra(usuarioId, pedidoId, valor);
        pedidoCompraDispatcher.send("LOJA_NOVO_PEDIDO", usuarioId, pedidoCompra);
    }

    private void email(KafkaDispatcher emailDispatcher) throws ExecutionException, InterruptedException {
        String assunto = "Processamento do pedido";
        String corpo = "Estamos processando seu pedido";

        Email email = new Email(assunto, corpo);
        emailDispatcher.send("LOJA_ENVIA_EMAIL", assunto, email);
    }
}
