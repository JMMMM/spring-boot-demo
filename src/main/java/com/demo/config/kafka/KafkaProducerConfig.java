package com.demo.config.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.ProducerFactoryUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Jimmy
 */
@Configuration
@EnableKafka
public class KafkaProducerConfig {
    private static Logger logger = LoggerFactory.getLogger(KafkaProducerConfig.class);

    private Map<String, Object> producerConfigs() {
        try {
            Properties props = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/kafka/kafkaConfig.properties"));
            return props;
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load 'ContextLoader.properties': " + ex.getMessage());
        }
        Map<String, Object> props = new HashMap<>(15);
        //kafka服务器地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
        //kafka消息序列化类 即将传入对象序列化为字节数组
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        //kafka消息key序列化类 若传入key的值，则根据该key的值进行hash散列计算出在哪个partition上
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //往kafka服务器提交消息间隔时间，0则立即提交不等待
        props.put(ProducerConfig.LINGER_MS_CONFIG, 0);
        return props;
    }

    private ProducerFactory getProducerFactory() {
    }

    @Bean
    public KafkaTemplate getKafkaTemplate() {
        KafkaTemplate kafkaTemplate = new KafkaTemplate(getProducerFactory());
        return kafkaTemplate;
    }
}
