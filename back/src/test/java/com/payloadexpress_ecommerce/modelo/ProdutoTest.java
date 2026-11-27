package com.payloadexpress_ecommerce.modelo;

import com.payloadexpress_ecommerce.excecao.EstoqueInsuficienteException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProdutoTest {
    
    private Produto produto;

    @BeforeEach 
    void prepararCenario() {
        produto = new Produto(
            "NOTE-001",
            "Notebook",
            "Notebook para testes",
            new BigDecimal("3000.00"),
            5
        );
    }

    @Test 
    @DisplayName("Deve criar produto com dados válidos") 
    void deveCriarProdutoComDadosValidos() {
        assertAll(
            () -> assertEquals("NOTE-001", produto.getCodigo()),
            () -> assertEquals("Notebook", produto.getNome()),
            () -> assertEquals(
                0,
                new BigDecimal("3000.00").compareTo(produto.getPreco())
            ),
            () -> assertEquals(5, produto.getQuantidadeEmEstoque()),
            () -> assertTrue(produto.isAtivo())
        );
    }

    @Test 
    @DisplayName("Deve identificar estoque suficiente") 
    void deveIdentificarEstoqueSuficiente() {
        assertTrue(produto.temEstoqueDisponivel(3));
    }

    @Test 
    @DisplayName("Deve identificar quando não há estoque suficiente") 
    void deveIdentificarEstoqueInsuficiente() {
        assertFalse(produto.temEstoqueDisponivel(6));
    }

    @Test 
    @DisplayName("Deve baixar estoque quando há unidades suficientes") 
    void deveBaixarEstoqueQuandoHaQuantidadeSuficiente() throws Exception {
        produto.baixarEstoque(2);

        assertEquals(3, produto.getQuantidadeEmEstoque());
    }

    @Test 
    @DisplayName("Deve repor o estoque") 
    void deveReporEstoque() {
        produto.reporEstoque(2);

        assertEquals(7, produto.getQuantidadeEmEstoque());
    }

    @Test 
    @DisplayName("Deve rejeitar preço igual a zero") 
    void deveRejeitarPrecoZerado() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Produto(
                "NOTE-002",
                "Notebook",
                "Notebook para testes",
                BigDecimal.ZERO,
                5
            )
        );
    }

    @Test 
    @DisplayName("Deve rejeitar estoque inicial negativo") 
    void deveRejeitarEstoqueInicialNegativo() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Produto(
                "NOTE-003",
                "Notebook",
                "Notebook para testes",
                new BigDecimal("100.00"),
                -1
            )
        );
    }

    @Test 
    @DisplayName("Deve rejeitar baixa de estoque quando a quantidade é insuficiente") 
    void deveRejeitarBaixaQuandoEstoqueInsuficiente() {
        assertThrows(
            EstoqueInsuficienteException.class,
            () -> produto.baixarEstoque(6)
        );

        assertEquals(5, produto.getQuantidadeEmEstoque());
    }

    @Test 
    @DisplayName("Deve rejeitar baixa de estoque com quantidade zero") 
    void deveRejeitarBaixaComQuantidadeZero() {
        assertThrows(
            IllegalArgumentException.class,
            () -> produto.baixarEstoque(0)
        );
    }

    @Test 
    @DisplayName("Não deve baixar estoque de produto inativo") 
    void naoDeveBaixarEstoqueDeProdutoInativo() {
        produto.desativar();

        assertThrows(
            IllegalStateException.class,
            () -> produto.baixarEstoque(1)
        );

        assertEquals(5, produto.getQuantidadeEmEstoque());
    }
}
