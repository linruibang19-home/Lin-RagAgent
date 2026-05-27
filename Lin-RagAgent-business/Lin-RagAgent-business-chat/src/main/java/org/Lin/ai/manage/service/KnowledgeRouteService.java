package org.Lin.ai.manage.service;

import org.Lin.ai.manage.model.route.KnowledgeRouteDecision;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 服务层
 * @author: Lin-RAGAgent
 **/
public interface KnowledgeRouteService {

    KnowledgeRouteDecision route(String question, String rewriteQuestion);

    void recordShadowRoute(String conversationId,
                           long exchangeId,
                           Long selectedDocumentId,
                           String question,
                           String rewriteQuestion);

    void recordAutoRoute(String conversationId,
                         long exchangeId,
                         String question,
                         String rewriteQuestion,
                         KnowledgeRouteDecision decision);
}
