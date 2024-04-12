package com.br.picpaydesafiobackend.Transaction;

import com.br.picpaydesafiobackend.authorization.AuthorizerService;
import com.br.picpaydesafiobackend.notification.NotificationService;
import com.br.picpaydesafiobackend.wallet.Wallet;
import com.br.picpaydesafiobackend.wallet.WalletRepository;
import com.br.picpaydesafiobackend.wallet.WalletType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AuthorizerService authorizerService;
    private final NotificationService notificationService;

    private TransactionService(TransactionRepository transactionRepository,
                               WalletRepository walletRepository, AuthorizerService authorizerService, NotificationService notificationService){
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizerService = authorizerService;
        this.notificationService = notificationService;
    }
    @Transactional
    public Transaction create(Transaction transaction){

        validade(transaction);

        var newTransaction =  transactionRepository.save(transaction);

        var walletPayer = walletRepository.findById(transaction.payer()).get();
        var walletPayee = walletRepository.findById(transaction.payee()).get();
        walletRepository.save(walletPayer.debit(transaction.value()));
        walletRepository.save(walletPayee.credit(transaction.value()));
        authorizerService.authorize(transaction);
        notificationService.notify(transaction);

        return newTransaction;
    }

    public List<Transaction> getAllTransaction(){
        var list=  transactionRepository.findAll();
        return list;
    }

    /*
    - the payer has a common wallet
    - the payer has enough balance
    - the payer is not the payee
     */
    private void validade(Transaction transaction) {
        walletRepository.findById(transaction.payee())
                .map(payee -> walletRepository.findById(transaction.payer())
                        .map(payer -> isTransactionValid(transaction, payer) ? transaction : null)
                        .orElseThrow(() -> new InvalidTransactionException("Invalid Transaction - %s".formatted(transaction))))
                .orElseThrow((() -> new InvalidTransactionException("Invalid Transaction - %s".formatted(transaction))));
    }

    private static boolean isTransactionValid(Transaction transaction, Wallet payer) {
        return payer.type() == WalletType.USER_COMUM.getValue() &&
                payer.balance().compareTo(transaction.value()) >= 0 &&
                !payer.id().equals(transaction.payee());
    }

}
