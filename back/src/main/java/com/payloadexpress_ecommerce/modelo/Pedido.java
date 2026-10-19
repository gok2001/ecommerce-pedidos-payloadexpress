package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.payloadexpress_ecommerce.excecao.EstoqueInsuficienteException;
import com.payloadexpress_ecommerce.excecao.PagamentoRecusadoException;
import com.payloadexpress_ecommerce.excecao.PedidoInvalidoException;

// Classe responsável pela criação de pedidos
public class Pedido {

    private final String numero;
    private final Cliente cliente;
    private final String data;
    private final List<ItemPedido> listaDeItens = new ArrayList<>();
    private SituacaoPedido situacaoPedido = SituacaoPedido.PENDENTE;
    private ProcessadorPagamento formaPagamento;
    private SituacaoPagamento situacaoPagamento = SituacaoPagamento.ABERTO;
    private String comprovante;

    public Pedido(String numero, Cliente cliente, String data, SituacaoPedido situacaoPedido) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("Número do pedido é obrigatório.");
        }

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente é obrigatório.");
        }

        if (data == null || data.isBlank()) {
            throw new IllegalArgumentException("Data é obrigatória.");
        }

        if (situacaoPedido != SituacaoPedido.PENDENTE) {
            throw new IllegalArgumentException("Novo pedido deve começar PENDENTE.");
        }

        this.numero = numero.trim();
        this.cliente = cliente;
        this.data = data.trim();
    }

    public String getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getData() {
        return data;
    }

    public SituacaoPedido getSituacaoPedido() {
        return situacaoPedido;
    }

    public SituacaoPagamento getSituacaoPagamento() {
        return situacaoPagamento;
    }

    public ProcessadorPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public String getComprovante() {
        return comprovante;
    }

    public List<ItemPedido> getListaDeItens() {
        return Collections.unmodifiableList(listaDeItens);
    }

    public void adicionarItem(Produto produto) throws EstoqueInsuficienteException {
        adicionarItem(produto, 1);
    }

    public void adicionarItem(Produto produto, int quantidade) throws EstoqueInsuficienteException {
        if (this.situacaoPagamento == SituacaoPagamento.PAGO) {
            throw new IllegalArgumentException("Não é possível adicionar itens ao pedido pago.");
        }

        if (!produto.temEstoqueDisponivel(quantidade)) {
            throw new EstoqueInsuficienteException(produto, quantidade);
        }

        ItemPedido itemPedido = new ItemPedido(produto, quantidade, produto.getPreco());
        listaDeItens.add(itemPedido);
        produto.baixarEstoque(quantidade);
    }

    public BigDecimal calcularValorTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedido item : listaDeItens) {
            total = total.add(item.calcularSubtotal());
        }

        return total;
    }
    
    public boolean pagar(ProcessadorPagamento formaPagamento) throws PedidoInvalidoException, PagamentoRecusadoException {
        validarPedidoParaPagamento();
        validarFormaPagamento(formaPagamento);

        BigDecimal valorTotal = calcularValorTotal();

        boolean processado = formaPagamento.processar(valorTotal);

        if (processado) {
            this.formaPagamento = formaPagamento;
            setSituacaoPagamento(SituacaoPagamento.PAGO);
            setSituacaoPedido(SituacaoPedido.PROCESSANDO);
        } else {
            throw new PagamentoRecusadoException(formaPagamento);
        }

        return processado;
    }

    private void validarPedidoParaPagamento() throws PedidoInvalidoException {
        if (situacaoPagamento == SituacaoPagamento.PAGO || situacaoPedido != SituacaoPedido.PENDENTE) {
            throw new PedidoInvalidoException("pedido já foi pago");
        }

        if (listaDeItens.isEmpty()) {
            throw new PedidoInvalidoException("pedido não possui itens");
        }
    }

    private void validarFormaPagamento(ProcessadorPagamento formaPagamento) {
        if (formaPagamento == null) {
            throw new IllegalArgumentException("Forma de pagamento não pode ser nula.");
        }
    }

    @Override
    public String toString() {
        return String.format(
            "Pedido{numero='%s', cliente=%s, data='%s', situacaoPedido='%s', situacaoPagamento='%s', listaDeItens=%s, valorTotal=%s}",
            numero,
            cliente,
            data,
            situacaoPedido,
            situacaoPagamento,
            listaDeItens,
            calcularValorTotal()
        );
    }
}
