package com.payloadexpress_ecommerce.excecao;

public class ClienteNaoEncontradoException extends ECommerceException {
    
    private final String identificador;

    public ClienteNaoEncontradoException(String identificador) {
        super("Cliente com id " + identificador + " não encontrado");

        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }
}
