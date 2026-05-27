package org.Lin.ai.manage.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 支撑组件
 * @author: Lin-RAGAgent
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentStrategyStepDraft {

    private String pipelineType;

    private Integer strategyType;

    private Integer strategyRole;

    private Integer sourceType;

    private String recommendReason;
}
