package com.payloadexpress_ecommerce.util;

import java.util.Random;

/**
 * Cálculos e formatações de apoio ao módulo de pedidos.
 * Classe utilitária: todos os métodos são estáticos
 */

public class PedidoUtils {

    private static final double VALOR_POR_QUILO = 7.50;
    private static final double FRETE_MINIMO = 15.00;
    private static final double TAXA_DESCONTO = 0.10;
    private static final double DESCONTO_MAXIMO = 50.00;
    private static final double VALOR_FRETE_GRATIS = 300.00;

    private PedidoUtils() {
        // classe utilitária não deve ser instanciada
    }

    private static String gerarNumeroDoPedido() {
        Random sorteio = new Random();
        int sequencial = sorteio.nextInt(100000);
        return String.format("PED-2026-%05d", sequencial);
    }
}
