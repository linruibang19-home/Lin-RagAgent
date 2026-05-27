package org.Lin.memory.model;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 模型对象
 * @author: Lin-RAGAgent
 **/
public record ComparisonTurnResponse(
    int round,
    String question,
    String noMemoryAnswer,
    String slidingWindowAnswer,
    String summaryAnswer,
    String summarySnapshot,
    int summaryCompressionCount
) {
}
