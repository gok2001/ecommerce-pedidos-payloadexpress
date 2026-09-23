package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;

public interface ProcessadorPagamento {
    boolean processar(BigDecimal valor);

    String getComprovante();
    String getDescricao();
}

