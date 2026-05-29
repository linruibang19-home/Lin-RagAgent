package org.Lin.ai.manage.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.Lin.ai.manage.mq.DocumentKafkaTopicNames;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 配置类
 * @author: Lin-RAGAgent
 **/

@EnableKafka
@Configuration
@EnableConfigurationProperties(DocumentManageProperties.class)
public class DocumentManageKafkaConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "app.manage.kafka", name = "auto-create-topics", havingValue = "true", matchIfMissing = true)
    public NewTopic documentParseTopic(DocumentKafkaTopicNames topicNames) {
        return TopicBuilder.name(topicNames.parseTopic()).partitions(1).replicas(1).build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.manage.kafka", name = "auto-create-topics", havingValue = "true", matchIfMissing = true)
    public NewTopic documentIndexTopic(DocumentKafkaTopicNames topicNames) {
        return TopicBuilder.name(topicNames.indexTopic()).partitions(1).replicas(1).build();
    }
}
