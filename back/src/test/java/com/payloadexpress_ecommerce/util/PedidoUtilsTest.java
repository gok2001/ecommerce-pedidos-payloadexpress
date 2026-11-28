package com.payloadexpress_ecommerce.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Year;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PedidoUtilsTest {

    private double[] precos;
    private int[] quantidades;
    private String[] nomes;

    @BeforeEach
    void prepararCenario() {
        precos = new double[] {10.00, 5.00};
        quantidades = new int[] {2, 3};
        nomes = new String[] {"Mouse", "Teclado"};
    }

    @Test
    @DisplayName("Deve calcular o subtotal dos itens")
    void deveCalcularSubtotal() {
        assertEquals(35.00, PedidoUtils.calcularSubtotal(precos, quantidades), 0.001);
    }

    @Test
    @DisplayName("Deve calcular o frete arredondando o peso para cima")
    void deveCalcularFretePeloPesoArredondado() {
        assertEquals(22.50, PedidoUtils.calcularFrete(2.3, 100.00), 0.001);
    }

    @Test
    @DisplayName("Deve aplicar o valor mínimo do frete")
    void deveAplicarFreteMinimo() {
        assertEquals(15.00, PedidoUtils.calcularFrete(1.0, 100.00), 0.001);
    }

    @Test
    @DisplayName("Deve oferecer frete grátis a partir do valor mínimo de compra")
    void deveOferecerFreteGratis() {
        assertEquals(0.00, PedidoUtils.calcularFrete(2.0, 300.00), 0.001);
    }

    @Test
    @DisplayName("Deve calcular desconto de dez por cento")
    void deveCalcularDesconto() {
        assertEquals(20.00, PedidoUtils.calcularDesconto(200.00), 0.001);
    }

    @Test
    @DisplayName("Deve limitar o desconto ao valor máximo")
    void deveLimitarDescontoMaximo() {
        assertEquals(50.00, PedidoUtils.calcularDesconto(1000.00), 0.001);
    }

    @Test
    @DisplayName("Deve normalizar um nome preenchido")
    void deveNormalizarNomePreenchido() {
        assertEquals("MOUSE GAMER", PedidoUtils.normalizarNome("  Mouse Gamer  "));
    }

    @Test
    @DisplayName("Deve usar o texto padrão quando o nome não foi informado")
    void deveUsarNomePadrao() {
        assertAll(
            () -> assertEquals("NAO INFORMADO", PedidoUtils.normalizarNome(null)),
            () -> assertEquals("NAO INFORMADO", PedidoUtils.normalizarNome("  "))
        );
    }

    @Test
    @DisplayName("Deve gerar número de pedido no formato esperado")
    void deveGerarNumeroDoPedido() {
        String numero = PedidoUtils.gerarNumeroDoPedido();
        String formatoEsperado = "PED-" + Year.now().getValue() + "-\\d{5}";

        assertTrue(numero.matches(formatoEsperado));
    }

    @Test
    @DisplayName("Deve montar recibo com todos os itens")
    void deveMontarRecibo() {
        String recibo = PedidoUtils.montarRecibo(nomes, precos, quantidades);

        assertAll(
            () -> assertTrue(recibo.startsWith("=== RECIBO ===")),
            () -> assertTrue(recibo.contains("Mouse")),
            () -> assertTrue(recibo.contains("Teclado"))
        );
    }

    @Test
    @DisplayName("Deve rejeitar listas de subtotal nulas ou incompatíveis")
    void deveRejeitarListasDeSubtotalInvalidas() {
        assertAll(
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> PedidoUtils.calcularSubtotal(null, quantidades)
            ),
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> PedidoUtils.calcularSubtotal(precos, new int[] {1})
            )
        );
    }

    @Test
    @DisplayName("Deve rejeitar preço negativo no cálculo do subtotal")
    void deveRejeitarPrecoNegativoNoSubtotal() {
        assertThrows(
            IllegalArgumentException.class,
            () -> PedidoUtils.calcularSubtotal(
                new double[] {-1.00},
                new int[] {1}
            )
        );
    }

    @Test
    @DisplayName("Deve rejeitar quantidade igual a zero no cálculo do subtotal")
    void deveRejeitarQuantidadeZeroNoSubtotal() {
        assertThrows(
            IllegalArgumentException.class,
            () -> PedidoUtils.calcularSubtotal(
                new double[] {10.00},
                new int[] {0}
            )
        );
    }

    @Test
    @DisplayName("Deve rejeitar peso inválido no cálculo do frete")
    void deveRejeitarPesoInvalido() {
        assertThrows(
            IllegalArgumentException.class,
            () -> PedidoUtils.calcularFrete(0.0, 100.00)
        );
    }

    @Test
    @DisplayName("Deve rejeitar subtotal negativo no cálculo do desconto")
    void deveRejeitarSubtotalNegativoNoDesconto() {
        assertThrows(
            IllegalArgumentException.class,
            () -> PedidoUtils.calcularDesconto(-1.00)
        );
    }

    @Test
    @DisplayName("Deve rejeitar quantidade inválida na linha do recibo")
    void deveRejeitarQuantidadeInvalidaNoRecibo() {
        assertThrows(
            IllegalArgumentException.class,
            () -> PedidoUtils.formatarLinhaDoRecibo("Mouse", 10.00, 0)
        );
    }

    @Test
    @DisplayName("Deve rejeitar listas incompatíveis ao montar o recibo")
    void deveRejeitarListasIncompativeisNoRecibo() {
        assertThrows(
            IllegalArgumentException.class,
            () -> PedidoUtils.montarRecibo(
                nomes,
                precos,
                new int[] {1}
            )
        );
    }
}