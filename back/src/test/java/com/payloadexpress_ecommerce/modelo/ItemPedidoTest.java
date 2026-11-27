package com.payloadexpress_ecommerce.modelo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ItemPedidoTest {
    
    private Produto produto;
    private ItemPedido item;

    @BeforeEach 
    void prepararCenario() {
        produto = new Produto(
            "MOUSE-001",
            "Mouse",
            "Mouse sem fio",
            new BigDecimal("100.00"),
            10
        );

        item = new ItemPedido(produto, 2, produto.getPreco());
    }

    @Test 
    @DisplayName("Deve criar item com dados válidos") 
    void deveCriarItemComDadosValidos() {
        assertAll(
            () -> assertSame(produto, item.getProduto()),
            () -> assertEquals(2, item.getQuantidade()),
            () -> assertEquals(
                0,
                new BigDecimal("100.00").compareTo(item.getPrecoPraticado())
            )
        );
    }

    @Test 
    @DisplayName("Deve calcular o subtotal multiplicando preço pela quantidade") 
    void deveCalcularSubtotal() {
        assertEquals(
            0,
            new BigDecimal("200.00").compareTo(item.calcularSubtotal())
        );
    }

    @Test 
    @DisplayName("Deve manter o preço praticado quando o preço do produto mudar") 
    void deveManterPrecoPraticadoDoItem() {
        produto.setPreco(new BigDecimal("150.00"));

        assertEquals(
            0,
            new BigDecimal("200.00").compareTo(item.calcularSubtotal())
        );
    }

    @Test 
    @DisplayName("Deve rejeitar produto nulo") 
    void deveRejeitarProdutoNulo() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new ItemPedido(null, 1, new BigDecimal("10.00"))
        );
    }

    @Test 
    @DisplayName("Deve rejeitar quantidade igual a zero") 
    void deveRejeitarQuantidadeZero() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new ItemPedido(produto, 0, new BigDecimal("10.00"))
        );
    }

    @Test 
    @DisplayName("Deve rejeitar preço praticado igual a zero") 
    void deveRejeitarPrecoPraticadoZerado() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new ItemPedido(produto, 1, BigDecimal.ZERO)
        );
    }
}
