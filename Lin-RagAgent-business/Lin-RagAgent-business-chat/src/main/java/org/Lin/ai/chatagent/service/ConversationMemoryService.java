package org.Lin.ai.chatagent.service;

import org.Lin.ai.chatagent.model.ConversationMemorySummaryView;
import org.Lin.ai.chatagent.model.memory.ConversationMemoryContext;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 服务层
 * @author: Lin-RAGAgent
 **/

public interface ConversationMemoryService {

    ConversationMemoryContext loadMemoryContext(String conversationId);

    default ConversationMemoryContext loadMemoryContext(String conversationId, ConversationTraceRecorder traceRecorder) {
        return loadMemoryContext(conversationId);
    }

    void refreshConversationSummaryAsync(String conversationId);

    ConversationMemorySummaryView getConversationSummary(String conversationId);

    ConversationMemorySummaryView rebuildConversationSummary(String conversationId);

    void deleteConversationSummary(String conversationId);
}
