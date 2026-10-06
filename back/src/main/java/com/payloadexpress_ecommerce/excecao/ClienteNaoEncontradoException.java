package com.payloadexpress_ecommerce.excecao;

public class ClienteNaoEncontradoException extends ECommerceException {
    
    private final String id;

    public ClienteNaoEncontradoException(String id) {
        super("Cliente com id " + id + " não encontrado");

        this.id = id;
    }

    public String getId() {
        return id;
    }
}
