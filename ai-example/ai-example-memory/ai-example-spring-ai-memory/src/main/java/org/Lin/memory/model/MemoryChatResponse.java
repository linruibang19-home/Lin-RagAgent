package org.Lin.memory.model;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 模型对象
 * @author: Lin-RAGAgent
 **/
public record MemoryChatResponse(
    String strategy,
    String sessionId,
    String question,
    String answer,
    int estimatedPromptTokens,
    String summary,
    int compressionCount,
    List<ConversationMessageView> memoryMessages
) {
}
