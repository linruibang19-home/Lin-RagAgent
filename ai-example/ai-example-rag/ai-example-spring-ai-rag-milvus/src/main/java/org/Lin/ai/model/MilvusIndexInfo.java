package org.Lin.ai.model;

import java.util.Map;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 模型对象
 * @author: Lin-RAGAgent
 **/
public record MilvusIndexInfo(
        String fieldName,
        String indexName,
        String state,
        long indexedRows,
        long totalRows,
        Map<String, String> params
) {
}
