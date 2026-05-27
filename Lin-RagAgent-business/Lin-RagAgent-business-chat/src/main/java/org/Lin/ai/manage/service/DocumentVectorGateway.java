package org.Lin.ai.manage.service;

import org.Lin.ai.manage.data.SuperAgentDocumentChunk;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 服务层
 * @author: Lin-RAGAgent
 **/

public interface DocumentVectorGateway {

    void vectorize(List<SuperAgentDocumentChunk> chunkList);

    void deleteByDocumentId(Long documentId);
}
