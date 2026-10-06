package com.payloadexpress_ecommerce.excecao;

public class PedidoInvalidoException extends ECommerceException {

    private final String motivo;

    public PedidoInvalidoException(String motivo) {
        super("Pedido inválido: " + motivo);

        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }
}
