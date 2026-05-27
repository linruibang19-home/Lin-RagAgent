package org.Lin.ai.model;

import jakarta.validation.constraints.NotBlank;

import java.util.Map;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 模型对象
 * @author: Lin-RAGAgent
 **/
public record MilvusChunkRequest(
        @NotBlank(message = "chunk id 不能为空")
        String id,
        @NotBlank(message = "content 不能为空")
        String content,
        @NotBlank(message = "docId 不能为空")
        String docId,
        @NotBlank(message = "category 不能为空")
        String category,
        String source,
        Map<String, Object> metadata
) {
}
