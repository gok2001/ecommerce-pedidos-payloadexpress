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
        validarEdicao();

        if (produto == null) {
            throw new IllegalArgumentException("Produto é obrigatório.");
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva.");
        }

        if (!produto.isAtivo()) {
            throw new IllegalStateException("Produto inativo: " + produto.getNome());
        }

        ItemPedido itemPedido = new ItemPedido(produto, quantidade, produto.getPreco());
        produto.baixarEstoque(quantidade);
        listaDeItens.add(itemPedido);
    }

    public BigDecimal calcularValorTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedido item : listaDeItens) {
            total = total.add(item.calcularSubtotal());
        }

        return total;
    }
    
    public boolean pagar(ProcessadorPagamento processador) throws PedidoInvalidoException, PagamentoRecusadoException {
        if (processador == null) {
            throw new IllegalArgumentException("Forma de pagamento é obrigatória.");
        }

        if (situacaoPagamento == SituacaoPagamento.PAGO || situacaoPedido != SituacaoPedido.PENDENTE) {
            throw new PedidoInvalidoException("somente pedidos pendentes e não pagos podem ser pagos.");
        }

        if (listaDeItens.isEmpty()) {
            throw new PedidoInvalidoException("pedido não possui itens");
        }

        BigDecimal total = calcularValorTotal();

        if (!processador.processar(total)) {
            throw new PagamentoRecusadoException(processador, "transação não aprovada (verifique limite ou compensação).");
        }

        this.formaPagamento = processador;
        this.comprovante = processador.getComprovante();
        this.situacaoPagamento = SituacaoPagamento.PAGO;
        this.situacaoPedido = SituacaoPedido.PROCESSANDO;

        return true;
    }

    public void marcarComoEnviado() throws PedidoInvalidoException{
        if (situacaoPedido != SituacaoPedido.PROCESSANDO || situacaoPagamento != SituacaoPagamento.PAGO) {
            throw new PedidoInvalidoException("pedido deve estar pago e PROCESSANDO para envio.");
        }

        situacaoPedido = SituacaoPedido.ENVIADO;
    }

    public void marcarComoEntregue() throws PedidoInvalidoException {
        if (situacaoPedido != SituacaoPedido.ENVIADO) {
            throw new PedidoInvalidoException("pedido deve estar ENVIADO para entrega.");
        }

        situacaoPedido = SituacaoPedido.ENTREGUE;
    }

    public void cancelar() throws PedidoInvalidoException{
        if (situacaoPedido != SituacaoPedido.PENDENTE || situacaoPagamento != SituacaoPagamento.ABERTO) {
            throw new PedidoInvalidoException("somente pedidos pendentes e não pagos podem ser cancelados.");
        }

        for (ItemPedido item : listaDeItens) {
            item.getProduto().reporEstoque(item.getQuantidade());
        }

        situacaoPedido = SituacaoPedido.CANCELADO;
    }

    private void validarEdicao() {
        if (situacaoPedido != SituacaoPedido.PENDENTE || situacaoPagamento != SituacaoPagamento.ABERTO) {
            throw new IllegalStateException("Somente pedidos pendentes e não pagos aceitam itens.");
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
