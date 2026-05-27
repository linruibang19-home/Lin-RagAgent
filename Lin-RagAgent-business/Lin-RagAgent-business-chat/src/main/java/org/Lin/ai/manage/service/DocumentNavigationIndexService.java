package org.Lin.ai.manage.service;

import org.Lin.ai.manage.data.SuperAgentDocumentStructureNode;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 服务层
 * @author: Lin-RAGAgent
 **/

public interface DocumentNavigationIndexService {

    void reindexDocumentNodes(Long documentId, Long parseTaskId, List<SuperAgentDocumentStructureNode> nodes);

    void deleteByDocumentId(Long documentId);

    List<NavigationSectionHit> searchSections(Long documentId,
                                              String topic,
                                              String facet,
                                              String informationNeed,
                                              String question,
                                              int size);

    record NavigationSectionHit(
        Long nodeId,
        String nodeCode,
        String title,
        String sectionPath,
        String canonicalPath,
        double score
    ) {
    }
}
