package com.br.picpaydesafiobackend.wallet;

public enum WalletType {

    USER_COMUM(1), USER_LOJISTA(2);
    private int value;

    private WalletType (int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
