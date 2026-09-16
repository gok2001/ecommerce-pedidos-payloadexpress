package com.payloadexpress_ecommerce.modelo;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

// Classe responsável pela criação de pedidos
public class Pedido {
    private String numero;
    private Cliente cliente;
    private String data;
    private String situacao;
    private List<ItemPedido> listaDeItens = new ArrayList<>();

    public Pedido(String numero, Cliente cliente, String data, String situacao) {
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

    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }

        this.cliente = cliente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        if (data == null || data.isBlank()) {
            throw new IllegalArgumentException("Data não pode ser nula ou vazia");
        }

        this.data = data;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        if (situacao == null || situacao.isBlank()) {
            throw new IllegalArgumentException("Situação não pode ser nula ou vazia.");
        }

        this.situacao = situacao;
    }

    public List<ItemPedido> getListaDeItens() {
        return listaDeItens;
    }

    public void adicionarItem(ItemPedido item) {
        listaDeItens.add(item);
    }

    public BigDecimal calcularValorTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedido item : listaDeItens) {
            total = total.add(item.calcularSubtotal());
        }

        return total;
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
