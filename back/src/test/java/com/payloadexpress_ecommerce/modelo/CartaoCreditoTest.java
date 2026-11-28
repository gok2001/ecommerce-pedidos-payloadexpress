package com.payloadexpress_ecommerce.modelo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartaoCreditoTest {

    private CartaoCredito cartao;

    @BeforeEach
    void prepararCenario() {
        cartao = new CartaoCredito(
            new BigDecimal("100.00"),
            LocalDate.now(),
            "**** 1234",
            "Visa",
            3,
            new BigDecimal("150.00")
        );
    }

    @Test
    @DisplayName("Deve aprovar compra quando o limite é igual ao valor")
    void deveAprovarCompraComLimiteExato() {
        CartaoCredito cartaoComLimiteExato = new CartaoCredito(
            new BigDecimal("100.00"),
            LocalDate.now(),
            "**** 1234",
            "Visa",
            1,
            new BigDecimal("100.00")
        );

        assertTrue(cartaoComLimiteExato.processar(new BigDecimal("100.00")));
    }

    @Test
    @DisplayName("Deve aprovar compra quando o limite é maior que o valor")
    void deveAprovarCompraComLimiteMaior() {
        assertTrue(cartao.processar(new BigDecimal("100.00")));
    }

    @Test
    @DisplayName("Deve gerar comprovante após aprovar a compra")
    void deveGerarComprovanteAposAprovacao() {
        cartao.processar(new BigDecimal("100.00"));

        assertAll(
            () -> assertTrue(cartao.getComprovante().startsWith("CARTAO-Visa-")),
            () -> assertEquals(
                "Cartão Visa **** 1234 em 3x",
                cartao.getDescricao()
            )
        );
    }

    @Test
    @DisplayName("Deve recusar compra quando o limite é insuficiente")
    void deveRecusarCompraComLimiteInsuficiente() {
        CartaoCredito cartaoSemLimite = new CartaoCredito(
            new BigDecimal("100.00"),
            LocalDate.now(),
            "**** 1234",
            "Visa",
            1,
            new BigDecimal("99.00")
        );

        assertFalse(cartaoSemLimite.processar(new BigDecimal("100.00")));
        assertNull(cartaoSemLimite.getComprovante());
    }

    @Test
    @DisplayName("Deve rejeitar quantidade de parcelas menor que um")
    void deveRejeitarParcelasInvalidas() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new CartaoCredito(
                new BigDecimal("100.00"),
                LocalDate.now(),
                "**** 1234",
                "Visa",
                0,
                new BigDecimal("150.00")
            )
        );
    }

    @Test
    @DisplayName("Deve rejeitar limite negativo")
    void deveRejeitarLimiteNegativo() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new CartaoCredito(
                new BigDecimal("100.00"),
                LocalDate.now(),
                "**** 1234",
                "Visa",
                1,
                new BigDecimal("-1.00")
            )
        );
    }

    @Test
    @DisplayName("Deve rejeitar cobrança diferente do valor do cartão")
    void deveRejeitarValorCobradoDiferente() {
        assertThrows(
            IllegalArgumentException.class,
            () -> cartao.processar(new BigDecimal("120.00"))
        );
    }
}