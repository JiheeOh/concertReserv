package com.hhplus.concertReserv.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;
import com.hhplus.concertReserv.domain.reservation.event.ReservationEvent;
import com.hhplus.concertReserv.infrastructure.kafka.KafkaMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServers;

    @Bean
    public ConsumerFactory<String, KafkaMessage<ReservationEvent>> consumerReservationFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "reservation_group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule()); // localDatetime 처리용
        JavaType type= om.getTypeFactory().constructParametricType(KafkaMessage.class, ReservationEvent.class);




        return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),new JsonDeserializer<KafkaMessage<ReservationEvent>>(type,om,false));
    }

    @Bean
    public ConsumerFactory<String, KafkaMessage<PaymentEvent>> consumerPaymentFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "payment_group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule()); // localDatetime 처리용
        JavaType type= om.getTypeFactory().constructParametricType(KafkaMessage.class, PaymentEvent.class);

        return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),new JsonDeserializer<KafkaMessage<PaymentEvent>>(type,om,false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaMessage<ReservationEvent>> containerReservationListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaMessage<ReservationEvent>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerReservationFactory());

        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.getContainerProperties().setAckTime(3000);
        factory.getContainerProperties().setPollTimeout(500);
        factory.getContainerProperties().setLogContainerConfig(true);
        factory.setConcurrency(1);
        return factory;

    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaMessage<PaymentEvent>> containerPaymentListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaMessage<PaymentEvent>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerPaymentFactory());

        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.getContainerProperties().setAckTime(3000);
        factory.getContainerProperties().setPollTimeout(500);
        factory.getContainerProperties().setLogContainerConfig(true);
        factory.setConcurrency(1);
        return factory;

    }
}
