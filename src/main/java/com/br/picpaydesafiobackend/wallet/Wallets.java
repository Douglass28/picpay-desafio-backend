package com.br.picpaydesafiobackend.wallet;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table(name = "WALLETS")
public record Wallets(

        @Id
        Long Id,
        String nome,
        String document,
        String email,
        String password,
        BigDecimal balance,
        WalletType walletType) {
}
