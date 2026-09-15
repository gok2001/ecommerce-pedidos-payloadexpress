package com.payloadexpress_ecommerce;
import com.payloadexpress_ecommerce.util.PedidoUtils;
import com.payloadexpress_ecommerce.modelo.Produto;
import com.payloadexpress_ecommerce.modelo.Cliente;
// import com.payloadexpress_ecommerce.modelo.Pedido;

public class App {

    public static void main(String[] args) {

        PedidoUtils.calcularFrete(); // chama calcularFrete
        
        Produto teclado = new Produto("TEC-001", "Teclatek", "Teclado Paraguai", 12.00, 2000);

        Produto monitor = new Produto("Tec-002", "Monitek", "Monitor Paraguai", 200, 1000);

        System.out.println(teclado);
        System.out.println(monitor);

        System.out.println(teclado.temEstoqueDisponivel(1300)); //true
        System.out.println(monitor.temEstoqueDisponivel(2000)); //false

        teclado.baixarEstoque(1300);
        System.out.println(teclado);

        Cliente cliente = new Cliente(
        "João",
        "123.456.789-00",
        "joao@email.com",
        "99999-9999",
        "Rua A, 123");

        System.out.println(cliente.retornaIdentificacaoCliente());
    }    
}