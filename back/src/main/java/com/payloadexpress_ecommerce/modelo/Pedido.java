package com.payloadexpress_ecommerce.modelo;

import java.util.ArrayList;
import java.util.List;

// Classe responsável pela criação de pedidos
public class Pedido {
    private String numero;
    private Cliente cliente = new Cliente();
    private String data;
    private String situacao;
    private List<String> listaDeItens = new ArrayList<>();
}
