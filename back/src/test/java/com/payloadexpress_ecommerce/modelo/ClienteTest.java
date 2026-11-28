package com.payloadexpress_ecommerce.modelo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void prepararCenario() {
        cliente = new Cliente(
            "Ana",
            "12345678901",
            "ana@email.com",
            "16999999999",
            "Rua A, 123"
        );
    }

    @Test
    @DisplayName("Deve criar cliente com dados válidos")
    void deveCriarClienteComDadosValidos() {
        assertAll(
            () -> assertEquals("Ana", cliente.getNome()),
            () -> assertEquals("12345678901", cliente.getDocumento()),
            () -> assertEquals("ana@email.com", cliente.getEmail()),
            () -> assertEquals("16999999999", cliente.getTelefone()),
            () -> assertEquals("Rua A, 123", cliente.getEndereco())
        );
    }

    @Test
    @DisplayName("Deve normalizar CPF, telefone e espaços dos dados")
    void deveNormalizarDadosDoCliente() {
        Cliente clienteFormatado = new Cliente(
            "  Ana  ",
            "123.456.789-01",
            " ana@email.com ",
            "(16) 99999-9999",
            " Rua A, 123 "
        );

        assertAll(
            () -> assertEquals("Ana", clienteFormatado.getNome()),
            () -> assertEquals("12345678901", clienteFormatado.getDocumento()),
            () -> assertEquals("ana@email.com", clienteFormatado.getEmail()),
            () -> assertEquals("16999999999", clienteFormatado.getTelefone()),
            () -> assertEquals("Rua A, 123", clienteFormatado.getEndereco())
        );
    }

    @Test
    @DisplayName("Deve montar a identificação do cliente")
    void deveMontarIdentificacaoDoCliente() {
        assertEquals(
            "Ana (CPF 12345678901)",
            cliente.getIdentificacao()
        );
    }

    @Test
    @DisplayName("Deve rejeitar email inválido")
    void deveRejeitarEmailInvalido() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Cliente(
                "Ana",
                "12345678901",
                "ana-sem-arroba.com",
                "16999999999",
                "Rua A, 123"
            )
        );
    }

    @Test
    @DisplayName("Deve rejeitar telefone com quantidade inválida de dígitos")
    void deveRejeitarTelefoneInvalido() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Cliente(
                "Ana",
                "12345678901",
                "ana@email.com",
                "123456789",
                "Rua A, 123"
            )
        );
    }

    @Test
    @DisplayName("Deve rejeitar endereço vazio")
    void deveRejeitarEnderecoVazio() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Cliente(
                "Ana",
                "12345678901",
                "ana@email.com",
                "16999999999",
                "   "
            )
        );
    }

    @Test
    @DisplayName("Deve rejeitar nome vazio")
    void deveRejeitarNomeVazio() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Cliente(
                "   ",
                "12345678901",
                "ana@email.com",
                "16999999999",
                "Rua A, 123"
            )
        );
    }

    @Test
    @DisplayName("Deve rejeitar CPF inválido")
    void deveRejeitarCpfInvalido() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Cliente(
                "Ana",
                "12345",
                "ana@email.com",
                "16999999999",
                "Rua A, 123"
            )
        );
    }
}