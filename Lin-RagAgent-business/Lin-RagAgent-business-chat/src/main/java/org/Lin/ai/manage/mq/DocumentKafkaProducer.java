package org.Lin.ai.manage.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.Lin.ai.manage.mq.message.DocumentIndexBuildMessage;
import org.Lin.ai.manage.mq.message.DocumentParseRouteMessage;
import org.Lin.enums.DocumentManageCode;
import org.Lin.exception.SuperAgentFrameException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 消息组件
 * @author: Lin-RAGAgent
 **/

@AllArgsConstructor
@Component
public class DocumentKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private final DocumentKafkaTopicNames topicNames;

    public void sendParseRoute(DocumentParseRouteMessage message) {

        send(topicNames.parseTopic(), String.valueOf(message.getDocumentId()), message);
    }

    public void sendIndexBuild(DocumentIndexBuildMessage message) {

        send(topicNames.indexTopic(), String.valueOf(message.getDocumentId()), message);
    }

    private void send(String topic, String key, Object message) {
        try {

            String payload = objectMapper.writeValueAsString(message);

            kafkaTemplate.send(topic, key, payload).get();
        } catch (Exception exception) {
            throw new SuperAgentFrameException(DocumentManageCode.KAFKA_SEND_FAILED.getCode(),
                "Kafka 消息发送失败: " + exception.getMessage(), exception);
        }
    }
}
