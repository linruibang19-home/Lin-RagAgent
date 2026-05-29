package org.Lin.ai.chatagent.rag.executor;

import org.Lin.ai.chatagent.rag.model.ExecutionMode;
import org.Lin.ai.chatagent.service.TaskInfo;
import reactor.core.publisher.Flux;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 统一对话执行器抽象
 * @author: Lin-RAGAgent
 **/

public interface ConversationExecutor {

    ExecutionMode mode();

    Flux<String> execute(TaskInfo taskInfo);
}
