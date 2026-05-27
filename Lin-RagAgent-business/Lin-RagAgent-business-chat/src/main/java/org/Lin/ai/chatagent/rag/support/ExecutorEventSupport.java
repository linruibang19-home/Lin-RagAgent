package org.Lin.ai.chatagent.rag.support;

import cn.hutool.core.util.StrUtil;
import org.Lin.ai.chatagent.service.TaskInfo;
import org.Lin.ai.chatagent.support.SinkEmitHelper;
import org.Lin.ai.chatagent.support.StreamEventWriter;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 支撑组件
 * @author: Lin-RAGAgent
 **/

public final class ExecutorEventSupport {

    private ExecutorEventSupport() {
    }

    public static void publishThinking(TaskInfo taskInfo, StreamEventWriter writer, String content) {
        if (taskInfo == null || writer == null || StrUtil.isBlank(content)) {
            return;
        }
        taskInfo.thinkingSteps().add(content);
        SinkEmitHelper.emitNext(taskInfo.sink(), writer.thinking(content, taskInfo.eventMetadata()));
    }

    public static void publishStatus(TaskInfo taskInfo, StreamEventWriter writer, String content) {
        if (taskInfo == null || writer == null || StrUtil.isBlank(content)) {
            return;
        }
        SinkEmitHelper.emitNext(taskInfo.sink(), writer.status(content, taskInfo.eventMetadata()));
    }
}
