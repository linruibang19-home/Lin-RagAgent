package org.Lin.ai.chatagent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 视图对象
 * @author: Lin-RAGAgent
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeDocumentOptionView {

    private String documentId;
    private String documentName;
    private String knowledgeScopeName;
    private String businessCategory;
    private String documentTags;
}
