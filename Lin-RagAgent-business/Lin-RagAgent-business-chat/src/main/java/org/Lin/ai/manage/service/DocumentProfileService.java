package org.Lin.ai.manage.service;

import org.Lin.ai.manage.data.SuperAgentDocumentProfile;
import org.Lin.ai.manage.data.SuperAgentDocumentStructureNode;
import org.Lin.ai.manage.support.DocumentAnalysisResult;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 服务层
 * @author: Lin-RAGAgent
 **/
public interface DocumentProfileService {

    SuperAgentDocumentProfile generateProfile(Long documentId,
                                              DocumentAnalysisResult analysisResult,
                                              List<SuperAgentDocumentStructureNode> structureNodes);

    SuperAgentDocumentProfile regenerateProfile(Long documentId);

    List<SuperAgentDocumentProfile> batchRegenerateProfiles(Collection<Long> documentIds);

    Optional<SuperAgentDocumentProfile> getByDocumentId(Long documentId);
}
