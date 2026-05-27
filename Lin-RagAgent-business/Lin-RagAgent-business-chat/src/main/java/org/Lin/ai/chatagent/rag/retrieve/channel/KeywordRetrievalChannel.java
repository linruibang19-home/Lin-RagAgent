package org.Lin.ai.chatagent.rag.retrieve.channel;

import cn.hutool.core.collection.CollectionUtil;
import org.Lin.ai.chatagent.rag.config.ChatRagProperties;
import org.Lin.ai.chatagent.rag.model.ConversationExecutionPlan;
import org.Lin.ai.chatagent.rag.service.DocumentRetrieveRequestFactory;
import org.Lin.ai.manage.service.DocumentKnowledgeService;
import org.Lin.enums.RetrievalChannelEnum;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 关键词检索通道
 * @author: Lin-RAGAgent
 **/

@Component
public class KeywordRetrievalChannel implements RetrievalChannel {

    private final DocumentKnowledgeService documentKnowledgeService;
    private final ChatRagProperties properties;
    private final DocumentRetrieveRequestFactory documentRetrieveRequestFactory;

    public KeywordRetrievalChannel(DocumentKnowledgeService documentKnowledgeService,
                                   ChatRagProperties properties,
                                   DocumentRetrieveRequestFactory documentRetrieveRequestFactory) {
        this.documentKnowledgeService = documentKnowledgeService;
        this.properties = properties;
        this.documentRetrieveRequestFactory = documentRetrieveRequestFactory;
    }

    @Override
    public String channelName() {
        return RetrievalChannelEnum.KEYWORD.getName();
    }

    @Override
    public boolean supports(ConversationExecutionPlan plan) {

        return properties.isKeywordChannelEnabled()
            && plan.getSelectedDocumentId() != null;
    }

    @Override
    public RetrievalChannelResult retrieve(String subQuestion, ConversationExecutionPlan plan) {
        List<Document> documentList = documentKnowledgeService.keywordSearch(
            documentRetrieveRequestFactory.build(subQuestion, plan, properties.getKeywordTopK())
        );

        return new RetrievalChannelResult(
            channelName(), documentList
        );
    }
}
