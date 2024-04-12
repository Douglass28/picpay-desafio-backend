package com.br.picpaydesafiobackend.wallet;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table(name = "WALLETS")
public record Wallet(
        @Id
        Long id,
        String nome,
        String document,
        String email,
        String password,
        BigDecimal balance,
        int type) {
        public Wallet debit(BigDecimal value){
                return new Wallet(id, nome, document, email, password, balance.subtract(value), type);
        }

        public Wallet credit(BigDecimal value){
                return new Wallet(id, nome, document, email, password, balance.add(value), type);
        }


}
