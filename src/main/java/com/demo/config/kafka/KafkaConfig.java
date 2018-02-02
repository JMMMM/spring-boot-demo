package com.demo.config.kafka;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;

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
        ProducerFactory producerFactory = new DefaultKafkaProducerFactory(KafkaUtils.kafkaProperties);
        return producerFactory;
    }

    @Bean
    @ConditionalOnMissingBean(ConsumerFactory.class)
    public ConsumerFactory kafkaConsumerFactory() {
        return new DefaultKafkaConsumerFactory(KafkaUtils.kafkaProperties);
    }

    @Bean
    @ConditionalOnMissingBean(ProducerListener.class)
    public ProducerListener<Object, Object> kafkaProducerListener() {
        return new LoggingProducerListener();
    }

    @Bean
    @ConditionalOnMissingBean(KafkaTemplate.class)
    public KafkaTemplate getKafkaTemplate() {
        KafkaTemplate kafkaTemplate = new KafkaTemplate(producerFactory());
        kafkaTemplate.setProducerListener(kafkaProducerListener());
        return kafkaTemplate;
    }
}
