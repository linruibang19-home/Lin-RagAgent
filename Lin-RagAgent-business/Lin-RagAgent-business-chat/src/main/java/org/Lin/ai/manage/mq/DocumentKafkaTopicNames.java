package org.Lin.ai.manage.mq;

import cn.hutool.core.util.StrUtil;
import org.Lin.ai.manage.config.DocumentManageProperties;
import org.Lin.constant.Constant;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Keeps Kafka topic names consistent across producers, consumers and auto-created topics.
 */
@Component
public class DocumentKafkaTopicNames {

    private final DocumentManageProperties properties;

    private final Environment environment;

    public DocumentKafkaTopicNames(DocumentManageProperties properties, Environment environment) {
        this.properties = properties;
        this.environment = environment;
    }

    public String parseTopic() {
        return runtimeTopicName(properties.getKafka().getParseTopic());
    }

    public String indexTopic() {
        return runtimeTopicName(properties.getKafka().getIndexTopic());
    }

    private String runtimeTopicName(String configuredTopicName) {
        String prefix = environment.getProperty(
            Constant.PREFIX_DISTINCTION_NAME,
            Constant.DEFAULT_PREFIX_DISTINCTION_NAME
        );
        String topicName = StrUtil.trim(configuredTopicName);
        if (StrUtil.isBlank(prefix) || StrUtil.startWith(topicName, prefix + "-")) {
            return topicName;
        }
        return prefix + "-" + topicName;
    }
}
