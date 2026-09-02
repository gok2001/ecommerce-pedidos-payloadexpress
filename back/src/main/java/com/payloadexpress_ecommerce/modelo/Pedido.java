package com.payloadexpress_ecommerce.modelo;

import java.util.ArrayList;
import java.util.List;

// Classe responsável pela criação de pedidos
public class Pedido {
    private String numero;
    private Cliente cliente;
    private String data;
    private String situacao;
    private List<ItemPedido> listaDeItens = new ArrayList<>();

    private Pedido(String numero, Cliente cliente, String data, String situacao, List<String> listaDeItens) {
        this.numero = numero;
        this.cliente = cliente;
        this.data = data;
        this.situacao = situacao;
        this.listaDeItens = listaDeItens;
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

    public String situacao() {
        return situacao;
    }

    public List<String> getListaDeItens() {
        return listaDeItens;
    }

    public void adicionarItem(ItemPedido item) {
        listaDeItens.add(item);
    }
}
