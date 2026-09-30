package com.payloadexpress_ecommerce.excecao;

public class ECommerceException extends Exception {

    public ECommerceException(String mensagem) {
        super(mensagem);
    }

    public ECommerceException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    
}