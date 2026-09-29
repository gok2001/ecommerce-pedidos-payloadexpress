package com.payloadexpress_ecommerce.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.math.BigDecimal;

// Classe responsável pela criação de pedidos
public class Pedido {

    private String numero;
    private Cliente cliente;
    private String data;
    private SituacaoPedido situacao;
    private List<ItemPedido> listaDeItens = new ArrayList<>();

    private ProcessadorPagamento formaPagamento;

    public Pedido(String numero, Cliente cliente, String data, SituacaoPedido situacao) {
        this.numero = numero;

        setCliente(cliente);
        setData(data);
        setSituacao(situacao);
    }

    public String getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Define o cliente associado ao pedido.
     * 
     * @param cliente cliente que será associado ao pedido.
     * @throws IllegalArgumentException se o cliente for nulo.
     */
    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }

        this.cliente = cliente;
    }

    public String getData() {
        return data;
    }

    /**
     * Define a data do pedido.
     * 
     * @param data data que será associada ao pedido.
     * @throws IllegalArgumentException se a data for nula ou estiver vazia.
     */
    public void setData(String data) {
        if (!campoValido(data)) {
            throw new IllegalArgumentException("Data não pode ser nula ou vazia.");
        }

        this.data = data;
    }

    public SituacaoPedido getSituacao() {
        return situacao;
    }

    /**
     * Define a situação atual do pedido.
     * 
     * @param situacao situação que será atribuída ao pedido.
     * @throws IllegalArgumentException se a situação for nula.
     */
    public void setSituacao(SituacaoPedido situacao) {
        if (situacao == null) {
            throw new IllegalArgumentException("Situação não pode ser nula.");
        }

        this.situacao = situacao;
    }

    private boolean campoValido(String campo) {
        return campo != null && !campo.isBlank();
    }

    public List<ItemPedido> getListaDeItens() {
        return Collections.unmodifiableList(listaDeItens);
    }

    public ProcessadorPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void adicionarItem(Produto produto) {
        adicionarItem(produto, 1);
    }

    public void adicionarItem(Produto produto, long quantidade) {
        ItemPedido itemPedido = new ItemPedido(produto, quantidade, produto.getPreco());
        listaDeItens.add(itemPedido);
    }

    public BigDecimal calcularValorTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedido item : listaDeItens) {
            total = total.add(item.calcularSubtotal());
        }

        return total;
    }
    
    public boolean pagar(ProcessadorPagamento formaPagamento) {
        validarPedidoParaPagamento();
        validarFormaPagamento(formaPagamento);

        BigDecimal valorTotal = calcularValorTotal();

        boolean processado = formaPagamento.processar(valorTotal);

        if (processado) {
            this.formaPagamento = formaPagamento;
        }

        return processado;
    }

    public boolean tentarFormasDePagamento(List<ProcessadorPagamento> formasPagamento) {
        validarPedidoParaPagamento();

        if (formasPagamento == null || formasPagamento.isEmpty()) {
            throw new IllegalArgumentException("A lista de formas de pagamento não pode ser nula ou vazia.");
        }

        BigDecimal valorTotal = calcularValorTotal();

        for (ProcessadorPagamento formaPagamento : formasPagamento) {
            validarFormaPagamento(formaPagamento);

            if (formaPagamento.processar(valorTotal)) {
                this.formaPagamento = formaPagamento;
                return true;
            }
        }

        return false;
    }

    private void validarPedidoParaPagamento() {
        if (listaDeItens.isEmpty()) {
            throw new IllegalStateException("Pedido não pode ser pago sem itens.");
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
            "Pedido{numero='%s', cliente=%s, data='%s', situacao='%s', listaDeItens=%s, valorTotal=%s}",
            numero,
            cliente,
            data,
            situacao,
            listaDeItens,
            calcularValorTotal()
        );
    }
}
