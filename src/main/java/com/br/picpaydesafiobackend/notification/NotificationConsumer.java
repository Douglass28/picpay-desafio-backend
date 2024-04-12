package com.br.picpaydesafiobackend.notification;

import com.br.picpaydesafiobackend.Transaction.Transactions;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class NotificationConsumer {

    private RestClient restClient;

    public NotificationConsumer(RestClient.Builder builder){
        this.restClient = builder
                .baseUrl("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6")
                .build();
    }

    @KafkaListener(topics = "transaction-notification", groupId = "picpay-desafio-backend")
    public void receiveNotification(Transactions transactions){

        var response = restClient.get()
                .retrieve()
                .toEntity(Notifications.class);
    }

}