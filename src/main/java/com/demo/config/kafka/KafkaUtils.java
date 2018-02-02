package com.demo.config.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class KafkaUtils {

    private static Logger logger = LoggerFactory.getLogger(KafkaUtils.class);

    public static Map<String, Object> kafkaProperties = loadKafkaProperties();

    private static Map<String, Object> loadKafkaProperties() {
        logger.info("loading Kafka properties ");
        try {
            Properties props = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/kafka/kafkaConfig.properties"));
            return props.entrySet().stream().collect(Collectors.toMap((entry) -> entry.getKey().toString(), (entry) -> entry.getValue()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
