package org.Lin.ai.manage.service;

import org.Lin.ai.manage.dto.DocumentProfileBatchRegenerateDto;
import org.Lin.ai.manage.dto.DocumentProfileDetailQueryDto;
import org.Lin.ai.manage.dto.DocumentProfileRegenerateDto;
import org.Lin.ai.manage.dto.KnowledgeRouteTraceQueryDto;
import org.Lin.ai.manage.dto.KnowledgeScopeDeleteDto;
import org.Lin.ai.manage.dto.KnowledgeScopeSaveDto;
import org.Lin.ai.manage.dto.KnowledgeTopicDeleteDto;
import org.Lin.ai.manage.dto.KnowledgeTopicQueryDto;
import org.Lin.ai.manage.dto.KnowledgeTopicSaveDto;
import org.Lin.ai.manage.dto.TopicDocumentRelationListQueryDto;
import org.Lin.ai.manage.dto.TopicDocumentRelationRemoveDto;
import org.Lin.ai.manage.dto.TopicDocumentRelationSaveDto;
import org.Lin.ai.manage.vo.DocumentProfileVo;
import org.Lin.ai.manage.vo.KnowledgeRouteTracePageVo;
import org.Lin.ai.manage.vo.KnowledgeScopeItemVo;
import org.Lin.ai.manage.vo.KnowledgeTopicItemVo;
import org.Lin.ai.manage.vo.TopicDocumentRelationItemVo;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 服务层
 * @author: Lin-RAGAgent
 **/
public interface KnowledgeManageService {

    KnowledgeScopeItemVo saveScope(KnowledgeScopeSaveDto dto);

    boolean deleteScope(KnowledgeScopeDeleteDto dto);

    List<KnowledgeScopeItemVo> listScopes();

    KnowledgeTopicItemVo saveTopic(KnowledgeTopicSaveDto dto);

    boolean deleteTopic(KnowledgeTopicDeleteDto dto);

    List<KnowledgeTopicItemVo> listTopics(KnowledgeTopicQueryDto dto);

    DocumentProfileVo queryProfile(DocumentProfileDetailQueryDto dto);

    DocumentProfileVo regenerateProfile(DocumentProfileRegenerateDto dto);

    List<DocumentProfileVo> batchRegenerateProfiles(DocumentProfileBatchRegenerateDto dto);

    List<TopicDocumentRelationItemVo> listTopicDocuments(TopicDocumentRelationListQueryDto dto);

    TopicDocumentRelationItemVo saveTopicDocumentRelation(TopicDocumentRelationSaveDto dto);

    boolean removeTopicDocumentRelation(TopicDocumentRelationRemoveDto dto);

    KnowledgeRouteTracePageVo queryRouteTracePage(KnowledgeRouteTraceQueryDto dto);
}
