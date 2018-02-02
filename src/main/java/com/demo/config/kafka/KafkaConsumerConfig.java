package com.demo.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jimmy
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    private Map<String, Object> getConsumerConfigs() {
        Map<String, Object> props = new HashMap<>(15);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
    }
}
