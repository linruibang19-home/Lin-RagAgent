package org.Lin.ai.chatagent.rag.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 文档问答路由结果
 * @author: Lin-RAGAgent
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentNavigationDecision {

    private DocumentNavigationAction navigationAction;

    private ExecutionMode executionMode;

    private ConversationStructureAnchor structureAnchor;

    private ConversationItemAnchor itemAnchor;

    private RetrievalQuestionPlan retrievalPlan;

    private String summaryText;

    @Builder.Default
    private List<String> queryContextHints = new ArrayList<>();

    @Builder.Default
    private List<String> softSectionHints = new ArrayList<>();
}
