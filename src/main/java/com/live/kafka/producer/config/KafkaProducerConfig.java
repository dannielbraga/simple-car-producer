package com.live.kafka.producer.config;

import com.live.kafka.producer.controller.CarDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${topic.name}")
    private String topic;

    @Value(value = "${spring.kafka.bootstrap-server}")
    private String bootstrapAddress;

    @Bean
    public NewTopic createTopic(){
        return new NewTopic(topic, 3, (short) 1);
    }

    @Bean
    public ProducerFactory<String, CarDTO> carProducerFactory(){
        Map<String, Object> configsProps = new HashMap<>();
        configsProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configsProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configsProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configsProps);
    }

    @Bean
    public KafkaTemplate<String, CarDTO> carKafkaTemplate(){
        return new KafkaTemplate<>(carProducerFactory());
    }
}
