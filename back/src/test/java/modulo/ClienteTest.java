package modulo;

import com.payloadexpress_ecommerce.modelo.Cliente;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClienteTest {
    
    @Test 
    void deveRejeitarEmailInvalido() {
        IllegalArgumentException erro = assertThrows(
            IllegalArgumentException.class,
            () -> new Cliente(
                "Ana",
                "12345678901",
                "ana-sem-arroba.com",
                "16999999999",
                "Rua A, 123"
            )
        );

        assertEquals(
            "Email inválido. Informe um endereço no formato nome@dominio.com",
            erro.getMessage()
        );
    }

    @Test 
    void deveAceitarEmailValido() {
        Cliente cliente = new Cliente(
            "Ana",
            "12345678901",
            "ana@email.com",
            "16999999999",
            "Rua A, 123"
        );

        assertEquals("ana@email.com", cliente.getEmail());
    }
}
