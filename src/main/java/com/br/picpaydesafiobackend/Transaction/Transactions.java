package com.br.picpaydesafiobackend.Transaction;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "TRANSACTIONS")
public record Transactions(
        @Id
        Long id,
        String payer,
        String payee,
        BigDecimal value,
        @CreatedDate LocalDateTime createdAt) {

    public Transactions{
        value.setScale(2);
    }
}
