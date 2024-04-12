package com.br.picpaydesafiobackend.Transaction;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "TRANSACTIONS")
public record Transaction(
        @Id
        Long id,
        Long payer,
        Long payee,
        BigDecimal value,
        @CreatedDate LocalDateTime createdAt) {

    public Transaction {
        value.setScale(2);
    }
}
