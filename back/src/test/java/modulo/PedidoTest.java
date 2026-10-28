package modulo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.payloadexpress_ecommerce.excecao.EstoqueInsuficienteException;
import com.payloadexpress_ecommerce.modelo.Cliente;
import com.payloadexpress_ecommerce.modelo.Pedido;
import com.payloadexpress_ecommerce.modelo.Produto;
import com.payloadexpress_ecommerce.modelo.SituacaoPedido;

public class PedidoTest {
    private Cliente cliente;
    private Produto notebook;
    private Pedido pedido;

    @BeforeEach
    void prepararCenario() {
        cliente = new Cliente("Ana", "12345678901","ana@email.com", "9999999999", "Rua Abu 123");
        notebook = new Produto("1" ,"Notebook", "Leve e potente",new BigDecimal("3000.00"), 5);
        pedido = new Pedido("1",cliente,"20-09-26", SituacaoPedido.PENDENTE);
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

}
