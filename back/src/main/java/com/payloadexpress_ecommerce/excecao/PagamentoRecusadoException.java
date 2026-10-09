package com.payloadexpress_ecommerce.excecao;

import com.payloadexpress_ecommerce.modelo.ProcessadorPagamento;;

public class PagamentoRecusadoException extends ECommerceException {
    
    private final String motivo;

    public PagamentoRecusadoException(ProcessadorPagamento formaPagamento, String motivo) {
        super("Pagamento por " + formaPagamento + " recusado: " + motivo);

        this.motivo = motivo;
    }

    public PagamentoRecusadoException(ProcessadorPagamento formaPagamento) {
        super("Pagamento por " + formaPagamento + " não foi aprovado");

        this.motivo = "Motivo não informado";
    }

    public String getMotivo() {
        return motivo;
    }
}
