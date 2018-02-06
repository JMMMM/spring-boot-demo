package com.demo.config.kafka;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.TopicPartitionInitialOffset;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author Jimmy
 */
@Configuration
@EnableKafka
public class KafkaConfig {
    @Bean
    @ConditionalOnMissingBean(ProducerFactory.class)
    public ProducerFactory producerFactory() {
        DefaultKafkaProducerFactory producerFactory = new DefaultKafkaProducerFactory(KafkaUtils.kafkaProperties);
        producerFactory.start();
        return producerFactory;
    }

    @Bean
    @ConditionalOnMissingBean(ConsumerFactory.class)
    public ConsumerFactory kafkaConsumerFactory() {
        return new DefaultKafkaConsumerFactory(KafkaUtils.kafkaProperties);
    }

    @Bean
    @ConditionalOnMissingBean(ProducerListener.class)
    public ProducerListener kafkaProducerListener() {
        return new LoggingProducerListener();
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerFactory());
        factory.setConcurrency(10);
        factory.getContainerProperties().setPollTimeout(1500);
        return factory;
    }

    @Bean
    @ConditionalOnMissingBean(KafkaTemplate.class)
    public KafkaTemplate kafkaTemplate() {
        KafkaTemplate kafkaTemplate = new KafkaTemplate(producerFactory());
        kafkaTemplate.setProducerListener(kafkaProducerListener());
        return kafkaTemplate;
    }

    @Bean
    public Listener listener() {
        return new Listener();
    }
}
