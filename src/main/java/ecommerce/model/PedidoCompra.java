package ecommerce.model;

import java.math.BigDecimal;

public class PedidoCompra {

    private String usuarioId;
    private String pedidoId;
    private BigDecimal valor;

    public PedidoCompra(String usuarioId, String pedidoId, BigDecimal valor) {
        this.usuarioId = usuarioId;
        this.pedidoId = pedidoId;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "PedidoCompra{" +
                "usuarioId='" + usuarioId + '\'' +
                ", pedidoId='" + pedidoId + '\'' +
                ", valor=" + valor +
                '}';
    }
}
