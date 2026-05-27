package org.Lin.ai.chatagent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Lin.ai.chatagent.model.trace.ConversationTraceStageView;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 视图对象
 * @author: Lin-RAGAgent
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationExchangeDetailView {

    private String conversationId;

    private ConversationExchangeView exchange;

    private List<ConversationTraceStageView> stageTraces;
}
