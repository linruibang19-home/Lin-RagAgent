package org.Lin.ai.model;

import java.util.Map;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 模型对象
 * @author: Lin-RAGAgent
 **/
public record MilvusSearchResult(
        String id,
        String content,
        Double score,
        String docId,
        String category,
        Map<String, Object> metadata
) {
}
