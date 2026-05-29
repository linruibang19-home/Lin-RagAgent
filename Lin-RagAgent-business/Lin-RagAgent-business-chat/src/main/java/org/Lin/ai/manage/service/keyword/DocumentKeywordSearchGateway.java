package org.Lin.ai.manage.service.keyword;

import org.Lin.ai.manage.data.SuperAgentDocumentChunk;
import org.Lin.ai.manage.model.DocumentRetrieveRequest;
import org.springframework.ai.document.Document;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 服务层
 * @author: Lin-RAGAgent
 **/

public interface DocumentKeywordSearchGateway {

    void indexChunks(List<SuperAgentDocumentChunk> chunkList);

    List<Document> search(DocumentRetrieveRequest request);

    void deleteByDocumentId(Long documentId);
}
