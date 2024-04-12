package com.br.picpaydesafiobackend.Transaction;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transactionRequest){
        return transactionService.create(transactionRequest);
    }

    @GetMapping
    public List<Transaction> getAllTransactions(){
       return transactionService.getAllTransaction();
    }
}
