package com.payloadexpress_ecommerce.excecao;

import com.payloadexpress_ecommerce.modelo.ProcessadorPagamento;

public class PagamentoRecusadoException extends ECommerceException {
    private final String motivo;

    public PagamentoRecusadoException(ProcessadorPagamento processador, String motivo) {
        super("Pagamento via " + processador.getDescricao() + " não aprovado: " + motivo);
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }
}