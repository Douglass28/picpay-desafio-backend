package com.br.picpaydesafiobackend.notification;

import com.br.picpaydesafiobackend.Transaction.Transactions;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private final KafkaTemplate<String, Transactions> kafkaTemplate;


    public NotificationProducer(KafkaTemplate<String, Transactions> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(Transactions transactions){
        kafkaTemplate.send("transaction-notification", transactions);
    }
}
