package com.nandom.account.service.kafka;

import com.nandom.account.constants.AppConstants;
import com.nandom.account.dto.WalletResponseDto;
import com.nandom.account.model.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate; 
import org.springframework.stereotype.Service;

@Service
public class KafKaProducerService 
{
    private static final Logger logger = LoggerFactory.getLogger(KafKaProducerService.class);
     
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
 
    public void sendMessage(WalletResponseDto message) {
        logger.info(String.format("Message sent -> %s", message));
        this.kafkaTemplate.send(AppConstants.TOPIC_NAME, message);
    }
}