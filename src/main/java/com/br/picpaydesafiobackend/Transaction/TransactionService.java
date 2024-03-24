package com.br.picpaydesafiobackend.Transaction;

import com.br.picpaydesafiobackend.exception.InvalidTransactionException;
import com.br.picpaydesafiobackend.wallet.Wallet;
import com.br.picpaydesafiobackend.wallet.WalletRepository;
import com.br.picpaydesafiobackend.wallet.WalletType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    private TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository){
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }
    @Transactional
    public Transactions create(Transactions transactions){

        validade(transactions);
        var newTransaction =  transactionRepository.save(transactions);
        var wallet = walletRepository.findById(transactions.payer()).get();
        walletRepository.save(wallet.debit(transactions.value()));

        //call the service externo
         // authorize transaction
        // send message

        return newTransaction;
    }

    /*
    - the payer has a common wallet
    - the payer has enough balance
    - the payer is not the payee
     */
    private void validade(Transactions transactions) {
        walletRepository.findById(transactions.payee())
                .map(payee -> walletRepository.findById(transactions.payer())
                        .map(payer -> isTransactionValid(transactions, payer) ? transactions : null)
                        .orElseThrow(() -> new InvalidTransactionException("Invalid Transaction - %s".formatted(transactions))))
                .orElseThrow((() -> new InvalidTransactionException("Invalid Transaction - %s".formatted(transactions))));
    }

    private static boolean isTransactionValid(Transactions transactions, Wallet payer) {
        return payer.type() == WalletType.USER_COMUM.getValue() &&
                payer.balance().compareTo(transactions.value()) >= 0 &&
                !payer.id().equals(transactions.payee());
    }

}
