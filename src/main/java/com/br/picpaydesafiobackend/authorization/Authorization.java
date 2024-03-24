package com.br.picpaydesafiobackend.authorization;

public record Authorization(String message) {

    public boolean isAuthorized(){
        return message.equals("Autorizado");
    }
}
