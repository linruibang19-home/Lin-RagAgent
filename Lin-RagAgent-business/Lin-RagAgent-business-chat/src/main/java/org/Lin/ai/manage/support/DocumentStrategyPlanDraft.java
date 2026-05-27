package org.Lin.ai.manage.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 支撑组件
 * @author: Lin-RAGAgent
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentStrategyPlanDraft {

    private String strategySnapshot;

    private String recommendReason;

    private List<DocumentStrategyStepDraft> parentSteps;

    private List<DocumentStrategyStepDraft> childSteps;
}
