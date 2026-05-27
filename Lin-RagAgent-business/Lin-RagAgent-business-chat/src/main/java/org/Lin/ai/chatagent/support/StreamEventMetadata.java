package org.Lin.ai.chatagent.support;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 支撑组件
 * @author: Lin-RAGAgent
 **/

public record StreamEventMetadata(
    String conversationId,
    Long exchangeId
) {
}
