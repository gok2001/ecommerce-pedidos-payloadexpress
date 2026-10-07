package modulo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.payloadexpress_ecommerce.excecao.EstoqueInsuficienteException;
import com.payloadexpress_ecommerce.modelo.Produto;

public class ProdutoTest {

    private Produto notebook;

    @BeforeEach
    void prepararCenario() {
        notebook = new Produto("1234", "Notebook", "De ultima geração", new BigDecimal(3000.00), 5);
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
