package com.payloadexpress_ecommerce.modelo;

import java.math.BigDecimal;

public class Pix extends FormaPagamento{

    private String chave;

    public Pix(BigDecimal valor, String chave){
        super(valor);
        setChave(chave);
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    @Override 
    public boolean processar(){
        return true;
    }

    @Override
    public String getResumo(){
        return super.getResumo()
            + " via Pix, chave "+ chave;
    }
}
