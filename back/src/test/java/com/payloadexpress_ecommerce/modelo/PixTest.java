package com.payloadexpress_ecommerce.modelo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PixTest {

    private Pix pix;

    @BeforeEach
    void prepararCenario() {
        pix = new Pix(
            new BigDecimal("100.00"),
            LocalDate.now(),
            "ana@email.com",
            TipoChavePix.EMAIL
        );
    }

    @Test
    @DisplayName("Deve processar pagamento Pix com chave de email válida")
    void deveProcessarPixComEmailValido() {
        assertTrue(pix.processar(new BigDecimal("100.00")));
    }

    @Test
    @DisplayName("Deve processar pagamento Pix com chave CPF válida")
    void deveProcessarPixComCpfValido() {
        Pix pixComCpf = new Pix(
            new BigDecimal("100.00"),
            LocalDate.now(),
            "12345678901",
            TipoChavePix.CPF
        );

        assertTrue(pixComCpf.processar(new BigDecimal("100.00")));
    }

    @Test
    @DisplayName("Deve gerar comprovante e manter o mesmo comprovante ao processar novamente")
    void deveManterComprovanteAoProcessarNovamente() {
        pix.processar(new BigDecimal("100.00"));
        String comprovanteOriginal = pix.getComprovante();

        pix.processar(new BigDecimal("100.00"));

        assertAll(
            () -> assertTrue(comprovanteOriginal.startsWith("PIX-")),
            () -> assertEquals(comprovanteOriginal, pix.getComprovante())
        );
    }

    @Test
    @DisplayName("Deve exibir a descrição da forma de pagamento Pix")
    void deveExibirDescricaoDoPix() {
        assertEquals("Pix (EMAIL) - ana@email.com", pix.getDescricao());
    }

    @Test
    @DisplayName("Deve rejeitar email incompatível com o tipo da chave Pix")
    void deveRejeitarChaveIncompativelComTipo() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Pix(
                new BigDecimal("100.00"),
                LocalDate.now(),
                "chave-invalida",
                TipoChavePix.EMAIL
            )
        );
    }

    @Test
    @DisplayName("Deve rejeitar chave Pix vazia")
    void deveRejeitarChaveVazia() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Pix(
                new BigDecimal("100.00"),
                LocalDate.now(),
                " ",
                TipoChavePix.EMAIL
            )
        );
    }

    @Test
    @DisplayName("Deve rejeitar cobrança diferente do valor do Pix")
    void deveRejeitarValorCobradoDiferente() {
        assertThrows(
            IllegalArgumentException.class,
            () -> pix.processar(new BigDecimal("120.00"))
        );
    }
}