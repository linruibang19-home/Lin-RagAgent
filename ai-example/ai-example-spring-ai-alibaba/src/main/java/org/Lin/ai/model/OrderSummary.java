package org.Lin.ai.model;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 模型对象
 * @author: Lin-RAGAgent
 **/
public record OrderSummary(
    String orderId,
    String status,
    boolean canRefund,
    String nextAction
) {
}
