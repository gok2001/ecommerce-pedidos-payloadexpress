package com.payloadexpress_ecommerce.modelo;

import com.payloadexpress_ecommerce.excecao.EstoqueInsuficienteException;
import com.payloadexpress_ecommerce.excecao.PagamentoRecusadoException;
import com.payloadexpress_ecommerce.excecao.PedidoInvalidoException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PedidoTest {

    private Cliente cliente;
    private Produto notebook;
    private Pedido pedido;

    @BeforeEach
    void prepararCenario() {
        cliente = new Cliente("Ana", "12345678901","ana@email.com", "9999999999", "Rua Abu 123");
        notebook = new Produto("1" ,"Notebook", "Leve e potente",new BigDecimal("3000.00"), 5);
        pedido = new Pedido("1", cliente,"2026-09-20", SituacaoPedido.PENDENTE);
    }

    @Test
    @DisplayName("Deve somar o total corretamente com dois itens")
    void deveSomarTotalComDoisItens() throws Exception {
        pedido.adicionarItem(notebook, 2);
        assertEquals(0, new BigDecimal("6000.00")
                .compareTo(pedido.calcularValorTotal()));
    }


    @Test
    @DisplayName("Deve baixar o estoque quando há quantidade suficiente")
    void deveBaixarEstoqueQuandoHaQuantidadeSuficiente() throws Exception {
        notebook.baixarEstoque(2); // Act
        assertEquals(3, notebook.getQuantidadeEmEstoque()); // Assert
    }

    @Test
    @DisplayName("Deve lançar exceção quando o estoque é insuficiente")
    void deveLancarExcecaoQuandoEstoqueInsuficiente() {
        EstoqueInsuficienteException erro = assertThrows(
                EstoqueInsuficienteException.class,
                () -> notebook.baixarEstoque(50));
        assertEquals(50, erro.getQuantidadeSolicitada());
        assertTrue(erro.getMessage().contains("Notebook"));
        assertEquals(5, notebook.getQuantidadeEmEstoque()); // não mexeu no estoque
    }

    @Test 
    void deveRejeitarDataPedidoInvalida() {
        IllegalArgumentException erro = assertThrows(
            IllegalArgumentException.class,
            () -> new Pedido("2", cliente, "2026-02-30", SituacaoPedido.PENDENTE)
        );

        assertTrue(erro.getMessage().contains("Use o formato AAAA-MM-DD"));
        assertTrue(erro.getCause() instanceof java.time.format.DateTimeParseException);
    }

    @Test 
    void deveAceitarDataDePedidoValida() {
        Pedido pedidoComDataValida = new Pedido("2", cliente, "2026-09-20", SituacaoPedido.PENDENTE);

        assertEquals("2026-09-20", pedidoComDataValida.getData());
    }

    @Test 
    void deveRejeitarPagamentoDePedidoSemItens() {
        Pix pix = new Pix(
            new BigDecimal("3000.00"),
            LocalDate.now(),
            "ana@email.com",
            TipoChavePix.EMAIL
        );

        PedidoInvalidoException erro = assertThrows(
            PedidoInvalidoException.class,
            () -> pedido.pagar(pix)
        );

        assertTrue(erro.getMessage().contains("não possui itens"));
    }

    @Test 
    void deveRejeitarPagamentoQuandoCartaoNaoTemLimite() throws Exception {
        pedido.adicionarItem(notebook, 1);

        CartaoCredito cartaoSemLimite = new CartaoCredito(
            pedido.calcularValorTotal(),
            LocalDate.now(),
            "**** 1234",
            "Visa",
            1,
            BigDecimal.ZERO
        );

        PagamentoRecusadoException erro = assertThrows(
            PagamentoRecusadoException.class,
            () -> pedido.pagar(cartaoSemLimite)
        );

        assertTrue(erro.getMessage().contains("não aprovado"));
        assertEquals(SituacaoPedido.PENDENTE, pedido.getSituacaoPedido());
    }

    @Test 
    void deveRejeitarSegundoPagamentoDoPedido() throws Exception {
        pedido.adicionarItem(notebook, 1);

        Pix pix = new Pix(
            pedido.calcularValorTotal(),
            LocalDate.now(),
            "ana@email.com",
            TipoChavePix.EMAIL
        );

        pedido.pagar(pix);

        PedidoInvalidoException erro = assertThrows(
            PedidoInvalidoException.class,
            () -> pedido.pagar(pix)
        );

        assertTrue(erro.getMessage().contains("não pagos"));
        assertEquals(SituacaoPedido.PROCESSANDO, pedido.getSituacaoPedido());
    }
}
