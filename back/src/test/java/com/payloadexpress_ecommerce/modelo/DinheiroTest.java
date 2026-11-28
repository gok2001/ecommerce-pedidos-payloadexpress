package com.payloadexpress_ecommerce.modelo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DinheiroTest {

    private Dinheiro dinheiro;

    @BeforeEach
    void prepararCenario() {
        dinheiro = new Dinheiro(new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("Deve criar pagamento em dinheiro com valor positivo")
    void deveCriarPagamentoEmDinheiro() {
        assertAll(
            () -> assertEquals("Dinheiro", dinheiro.getDescricao()),
            () -> assertEquals(0, BigDecimal.ZERO.compareTo(dinheiro.getTroco()))
        );
    }

    @Test
    @DisplayName("Deve aceitar pagamento com valor exato e sem troco")
    void deveAceitarValorExato() {
        assertTrue(dinheiro.processar(new BigDecimal("100.00")));

        assertAll(
            () -> assertEquals(0, BigDecimal.ZERO.compareTo(dinheiro.getTroco())),
            () -> assertTrue(dinheiro.getComprovante().startsWith("DINHEIRO-"))
        );
    }

    @Test
    @DisplayName("Deve aceitar pagamento e calcular o troco")
    void deveCalcularTroco() {
        Dinheiro pagamentoComTroco = new Dinheiro(new BigDecimal("150.00"));

        assertTrue(pagamentoComTroco.processar(new BigDecimal("100.00")));

        assertEquals(
            0,
            new BigDecimal("50.00").compareTo(pagamentoComTroco.getTroco())
        );
    }

    @Test
    @DisplayName("Deve recusar pagamento quando o valor recebido é insuficiente")
    void deveRecusarValorInsuficiente() {
        Dinheiro pagamentoInsuficiente = new Dinheiro(new BigDecimal("90.00"));

        assertFalse(pagamentoInsuficiente.processar(new BigDecimal("100.00")));

        assertAll(
            () -> assertEquals(0, BigDecimal.ZERO.compareTo(pagamentoInsuficiente.getTroco())),
            () -> assertNull(pagamentoInsuficiente.getComprovante())
        );
    }

    @Test
    @DisplayName("Deve rejeitar valor recebido igual a zero")
    void deveRejeitarValorRecebidoZerado() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Dinheiro(BigDecimal.ZERO)
        );
    }

    @Test
    @DisplayName("Deve rejeitar valor cobrado igual a zero")
    void deveRejeitarValorCobradoZerado() {
        assertThrows(
            IllegalArgumentException.class,
            () -> dinheiro.processar(BigDecimal.ZERO)
        );
    }

    @Test
    @DisplayName("Não deve processar novamente um pagamento já concluído")
    void deveRejeitarSegundoProcessamento() {
        dinheiro.processar(new BigDecimal("100.00"));

        assertThrows(
            IllegalStateException.class,
            () -> dinheiro.processar(new BigDecimal("100.00"))
        );
    }
}