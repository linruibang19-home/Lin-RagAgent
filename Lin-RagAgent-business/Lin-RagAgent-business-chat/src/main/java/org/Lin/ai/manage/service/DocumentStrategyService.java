package org.Lin.ai.manage.service;

import org.Lin.ai.manage.data.SuperAgentDocument;
import org.Lin.ai.manage.data.SuperAgentDocumentStrategyPlan;
import org.Lin.ai.manage.data.SuperAgentDocumentStrategyStep;
import org.Lin.ai.manage.support.DocumentAnalysisResult;
import org.Lin.ai.manage.support.DocumentStrategyPlanDraft;
import org.Lin.ai.manage.support.ParentBlockCandidate;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 服务层
 * @author: Lin-RAGAgent
 **/

public interface DocumentStrategyService {

    DocumentStrategyPlanDraft recommendStrategy(SuperAgentDocument document, DocumentAnalysisResult analysisResult);

    List<SuperAgentDocumentStrategyStep> normalizeSteps(SuperAgentDocumentStrategyPlan basePlan,
                                                        List<SuperAgentDocumentStrategyStep> baseSteps,
                                                        List<Integer> requestParentStrategyTypes,
                                                        List<Integer> requestChildStrategyTypes,
                                                        Long documentId);

    List<ParentBlockCandidate> buildParentBlocks(SuperAgentDocument document,
                                                 SuperAgentDocumentStrategyPlan plan,
                                                 List<SuperAgentDocumentStrategyStep> steps,
                                                 String parsedText);
}
