package org.Lin.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 配置类
 * @author: Lin-RAGAgent
 **/
@Configuration
public class SpringAiChatConfig {

    @Bean
    public ChatClient chatClient(ChatModel chatModel,
                                 @Value("${app.ai.chat.default-system-prompt}") String defaultSystemPrompt) {
        return ChatClient.builder(chatModel)
            .defaultSystem(defaultSystemPrompt)
            .defaultAdvisors(new SimpleLoggerAdvisor())
            .build();
    }

}
