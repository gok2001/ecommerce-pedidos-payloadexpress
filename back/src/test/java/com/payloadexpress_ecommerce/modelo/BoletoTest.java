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

class BoletoTest {

    private Boleto boleto;

    @BeforeEach
    void prepararCenario() {
        boleto = new Boleto(
            new BigDecimal("100.00"),
            LocalDate.now(),
            "BOL-12345",
            LocalDate.now().plusDays(5)
        );
    }

    @Test
    @DisplayName("Deve criar boleto com dados válidos")
    void deveCriarBoletoComDadosValidos() {
        assertAll(
            () -> assertEquals("BOL-12345", boleto.getCodigoDeBarras()),
            () -> assertEquals(LocalDate.now().plusDays(5), boleto.getDataDeVencimento()),
            () -> assertEquals("Boleto BOL-12345", boleto.getDescricao())
        );
    }

    @Test
    @DisplayName("Deve permanecer sem comprovante antes da compensação")
    void deveAguardarCompensacao() {
        assertFalse(boleto.processar(new BigDecimal("100.00")));
        assertNull(boleto.getComprovante());
    }

    @Test
    @DisplayName("Deve processar boleto após a compensação")
    void deveProcessarBoletoCompensado() {
        boleto.confirmarCompensacao();

        assertTrue(boleto.processar(new BigDecimal("100.00")));
        assertTrue(boleto.getComprovante().startsWith("BOLETO-"));
    }

    @Test
    @DisplayName("Deve manter o comprovante ao processar novamente")
    void deveManterComprovanteAoProcessarNovamente() {
        boleto.confirmarCompensacao();
        boleto.processar(new BigDecimal("100.00"));
        String comprovanteOriginal = boleto.getComprovante();

        boleto.processar(new BigDecimal("100.00"));

        assertEquals(comprovanteOriginal, boleto.getComprovante());
    }

    @Test
    @DisplayName("Deve rejeitar código de barras vazio")
    void deveRejeitarCodigoDeBarrasVazio() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Boleto(
                new BigDecimal("100.00"),
                LocalDate.now(),
                " ",
                LocalDate.now().plusDays(5)
            )
        );
    }

    @Test
    @DisplayName("Deve rejeitar vencimento no passado")
    void deveRejeitarVencimentoNoPassado() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Boleto(
                new BigDecimal("100.00"),
                LocalDate.now(),
                "BOL-12345",
                LocalDate.now().minusDays(1)
            )
        );
    }

    @Test
    @DisplayName("Deve rejeitar cobrança diferente do valor do boleto")
    void deveRejeitarValorCobradoDiferente() {
        boleto.confirmarCompensacao();

        assertThrows(
            IllegalArgumentException.class,
            () -> boleto.processar(new BigDecimal("120.00"))
        );
    }
}