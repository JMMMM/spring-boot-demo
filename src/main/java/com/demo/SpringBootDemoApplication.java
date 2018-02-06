package com.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jimmy
 */
@SpringBootApplication
@Component
@Controller
public class SpringBootDemoApplication {
    @Autowired
    private KafkaTemplate<String, Object> template;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

    @ResponseBody
    @RequestMapping(value = "sendMessage")
    public Object haha() throws Exception {
        template.send("topic1", "test1");
        template.send("topic1", "test2");
        template.send("topic1", "test3");
        return null;
    }

}
