package org.Lin.ai.manage.service;

import org.Lin.ai.manage.data.SuperAgentDocumentStructureNode;
import org.Lin.ai.manage.support.DocumentStructureNodeCandidate;

import java.util.List;
import java.util.Map;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 服务层
 * @author: Lin-RAGAgent
 **/

public interface DocumentStructureNodeService {

    List<SuperAgentDocumentStructureNode> replaceDocumentNodes(Long documentId,
                                                               Long parseTaskId,
                                                               List<DocumentStructureNodeCandidate> candidates);

    List<SuperAgentDocumentStructureNode> listDocumentNodes(Long documentId, Long parseTaskId);

    Map<Long, SuperAgentDocumentStructureNode> nodeMap(Long documentId, Long parseTaskId);

    void deleteByDocumentId(Long documentId);
}
