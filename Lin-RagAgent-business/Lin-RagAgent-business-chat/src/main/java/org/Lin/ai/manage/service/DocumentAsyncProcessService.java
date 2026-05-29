package org.Lin.ai.manage.service;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 服务层
 * @author: Lin-RAGAgent
 **/

public interface DocumentAsyncProcessService {

    void handleParseRoute(Long documentId, Long taskId);

    void handleIndexBuild(Long documentId, Long taskId, Long planId);
}
