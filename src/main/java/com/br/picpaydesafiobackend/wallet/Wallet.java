package com.br.picpaydesafiobackend.wallet;

import com.br.picpaydesafiobackend.Transaction.Transactions;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.transaction.annotation.Transactional;

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
}
