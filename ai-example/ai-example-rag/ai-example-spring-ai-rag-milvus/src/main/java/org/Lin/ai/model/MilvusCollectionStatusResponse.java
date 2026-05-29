package org.Lin.ai.model;

import java.util.List;
import java.util.Map;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 模型对象
 * @author: Lin-RAGAgent
 **/
public record MilvusCollectionStatusResponse(
        String uri,
        String databaseName,
        String collectionName,
        boolean exists,
        String loadState,
        Map<String, String> statistics,
        List<MilvusIndexInfo> indexes,
        String collectionSummary
) {
}
