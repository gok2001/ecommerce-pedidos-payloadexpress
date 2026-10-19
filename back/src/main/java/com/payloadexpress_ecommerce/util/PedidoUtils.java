package com.payloadexpress_ecommerce.util;

import java.time.Year;
import java.util.Random;

public final class PedidoUtils {

    private static final double VALOR_POR_QUILO = 7.50;
    private static final double FRETE_MINIMO = 15.00;
    private static final double TAXA_DESCONTO = 0.10;
    private static final double DESCONTO_MAXIMO = 50.00;
    private static final double VALOR_FRETE_GRATIS = 300.00;
    private static final Random SORTEIO = new Random();

    private PedidoUtils() {

    }

    public static String gerarNumeroDoPedido() {
        return String.format("PED-%d-%05d", Year.now().getValue(), SORTEIO.nextInt(100000));
    }

    public static double calcularSubtotal(double[] precos, int[] quantidades) {
        if (precos == null || quantidades == null || precos.length == 0 || precos.length != quantidades.length) {
            throw new IllegalArgumentException("Listas de preços/quantidades inválidas.");
        }

        double subtotal = 0;

        for (int i = 0; i < precos.length; i++) {
            if (!Double.isFinite(precos[i]) || precos[i] < 0 || quantidades[i] <= 0) {
                throw new IllegalArgumentException("Preço ou quantidade inválida no item " + i);
            }

            subtotal += precos[i] * quantidades[i];
        }

        return subtotal;
    }

    public static double calcularFrete(double pesoEmQuilos, double subtotal) {
        if (!Double.isFinite(pesoEmQuilos) || pesoEmQuilos <= 0 || !Double.isFinite(subtotal) || subtotal < 0) {
            throw new IllegalArgumentException("Peso e subtotal devem ser válidos.");
        }

        if (subtotal >= VALOR_FRETE_GRATIS) return 0;

        return Math.max(Math.ceil(pesoEmQuilos) * VALOR_POR_QUILO, FRETE_MINIMO);
    }

    public static double calcularDesconto(double subtotal) {
        if (!Double.isFinite(subtotal) || subtotal < 0) {
            throw new IllegalArgumentException("Subtotal deve ser válido e não negativo.");
        }

        return Math.min(subtotal * TAXA_DESCONTO, DESCONTO_MAXIMO);
    }

    public static String normalizarNome(String nome) {
        return nome == null || nome.isBlank() ? "NAO INFORMADO" : nome.trim().toUpperCase();
    }

    public static String formatarLinhaDoRecibo(String nome, double preco, int quantidade) {
        if (nome == null || nome.isBlank() || !Double.isFinite(preco) || preco < 0 || quantidade <= 0) {
            throw new IllegalArgumentException("Item inválido para recibo.");
        }

        return String.format("%-20s R$ %8.2f x%3d", nome, preco, quantidade);
    }

    public static String montarRecibo(String[] nomes, double[] precos, int[] quantidades) {
        if (nomes == null || precos == null || quantidades == null
                || nomes.length == 0 || nomes.length != precos.length || nomes.length != quantidades.length) {
            throw new IllegalArgumentException("Dados do recibo inválidos.");
        }

        StringBuilder sb = new StringBuilder("=== RECIBO ===").append(System.lineSeparator());

        for (int i = 0; i < nomes.length; i++) {
            sb.append(formatarLinhaDoRecibo(nomes[i], precos[i], quantidades[i])).append(System.lineSeparator());
        }

        return sb.toString();
    }
}