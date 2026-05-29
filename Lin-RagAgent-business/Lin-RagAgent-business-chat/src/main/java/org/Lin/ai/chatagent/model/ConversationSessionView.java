package org.Lin.ai.chatagent.model;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Lin.enums.ChatQueryMode;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 视图对象
 * @author: Lin-RAGAgent
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationSessionView {

    private String conversationId;
    private boolean running;
    private int checkpointCount;
    private int messageCount;
    private String latestUserMessage;
    private String latestAssistantMessage;
    private Long latestExchangeId;
    private String latestTurnStatus;
    private String latestTurnErrorMessage;
    private ChatQueryMode chatMode;
    private String selectedDocumentId;
    private String selectedDocumentName;
    private Instant createdAt;
    private Instant updatedAt;
    private List<ConversationExchangeView> exchanges;
    private ConversationMemorySummaryView memorySummary;
}
