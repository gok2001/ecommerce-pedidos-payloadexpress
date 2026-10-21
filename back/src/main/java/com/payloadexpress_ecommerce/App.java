package com.payloadexpress_ecommerce;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.payloadexpress_ecommerce.excecao.ClienteNaoEncontradoException;
import com.payloadexpress_ecommerce.excecao.EstoqueInsuficienteException;
import com.payloadexpress_ecommerce.excecao.PagamentoRecusadoException;
import com.payloadexpress_ecommerce.excecao.PedidoInvalidoException;
import com.payloadexpress_ecommerce.modelo.*;
import com.payloadexpress_ecommerce.util.PedidoUtils;

public class App {
    public static void main(String[] args) {
        Map<String, Cliente> clientes = new HashMap<>();
        Map<String, Produto> produtos = new HashMap<>();

        try {
            Cliente cliente = new Cliente("João", "123.456.789-00", "joao@email.com", "16999999999", "Rua A, 123");
            cadastrarCliente(clientes, cliente);
            Cliente encontrado = buscarCliente(clientes, "12345678900");

            Produto teclado = new Produto("TEC-001", "Teclado", "Teclado mecânico", new BigDecimal("120.00"), 5);
            cadastrarProduto(produtos, teclado);

            Pedido pedido = new Pedido(PedidoUtils.gerarNumeroDoPedido(), encontrado, LocalDate.now().toString(), SituacaoPedido.PENDENTE);
            pedido.adicionarItem(teclado, 2);

            System.out.println(pedido);
            System.out.println("Estoque após reserva: " + teclado.getQuantidadeEmEstoque());

            try {
                pedido.adicionarItem(teclado, 50);
            } catch (EstoqueInsuficienteException e) {
                System.out.println("Estoque indisponível: " + e.getMessage());
            }

            Boleto boleto = new Boleto(pedido.calcularValorTotal(), LocalDate.now(), "BOL-12345", LocalDate.now().plusDays(5));

            try {
                pedido.pagar(boleto);
            } catch (PagamentoRecusadoException e) {
                System.out.println("Pagamento pendente: " + e.getMessage());
            }

            Pix pix = new Pix(pedido.calcularValorTotal(), LocalDate.now(), "joao@email.com", TipoChavePix.EMAIL);
            pedido.pagar(pix);

            System.out.println("Após Pix: " + pedido);
            System.out.println("Comprovante: " + pedido.getComprovante());

            try {
                pedido.pagar(pix);
            } catch (PedidoInvalidoException e) {
                System.out.println("Pagamento duplicado bloqueado: " + e.getMessage());
            }

            testarPolimorfismo();

            System.out.println(PedidoUtils.montarRecibo(new String[]{"TECLADO"}, new double[]{120.00}, new int[]{2}));
            System.out.printf("Frete: R$ %.2f%n", PedidoUtils.calcularFrete(2.3, 240.00));

        } catch (ClienteNaoEncontradoException | EstoqueInsuficienteException | PagamentoRecusadoException | PedidoInvalidoException e) {
            System.out.println("Não foi possível concluir: " + e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Dados ou operação inválidos: " + e.getMessage());
        }
    }

    private static void cadastrarCliente(Map<String, Cliente> clientes, Cliente cliente) {
        if (cliente == null) throw new IllegalArgumentException("Cliente é obrigatório.");

        if (clientes.containsKey(cliente.getDocumento())) {
            throw new IllegalArgumentException("Cliente já cadastrado: " + cliente.getDocumento());
        }

        clientes.put(cliente.getDocumento(), cliente);
    }

    private static void cadastrarProduto(Map<String, Produto> produtos, Produto produto) {
        if (produto == null) throw new IllegalArgumentException("Produto é obrigatório.");

        if (produtos.containsKey(produto.getCodigo())) {
            throw new IllegalArgumentException("Produto já cadastrado: " + produto.getCodigo());
        }

        produtos.put(produto.getCodigo(), produto);
    }

    private static Cliente buscarCliente(Map<String, Cliente> clientes, String documento) throws ClienteNaoEncontradoException {
        Cliente cliente = clientes.get(documento);

        if (cliente == null) throw new ClienteNaoEncontradoException(documento);

        return cliente;
    }

    private static void testarPolimorfismo() {
        BigDecimal valor = new BigDecimal("50.00");
        List<ProcessadorPagamento> formas = List.of(
            new Pix(valor, LocalDate.now(), "teste@email.com", TipoChavePix.EMAIL),
            new Boleto(valor, LocalDate.now(), "BOL-50", LocalDate.now().plusDays(3)),
            new CartaoCredito(valor, LocalDate.now(), "**** 1234", "Visa", 3, new BigDecimal("100.00")),
            new Dinheiro(new BigDecimal("100.00"))
        );

        for (ProcessadorPagamento forma : formas) {
            boolean aprovado = forma.processar(valor);

            System.out.println(forma.getDescricao() + " -> " + (aprovado ? "Aprovado: " + forma.getComprovante() : "Não aprovado"));
        }
    }
}