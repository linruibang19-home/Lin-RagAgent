package org.Lin.ai.chatagent.rag.retrieve.channel;

import org.Lin.ai.chatagent.rag.model.ConversationExecutionPlan;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 检索通道抽象
 * @author: Lin-RAGAgent
 **/

public interface RetrievalChannel {

    String channelName();

    boolean supports(ConversationExecutionPlan plan);

    RetrievalChannelResult retrieve(String subQuestion, ConversationExecutionPlan plan);
}
