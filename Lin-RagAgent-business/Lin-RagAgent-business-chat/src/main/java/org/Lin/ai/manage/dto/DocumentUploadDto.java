package org.Lin.ai.manage.dto;

import lombok.Data;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 数据传输对象
 * @author: Lin-RAGAgent
 **/

@Data
public class DocumentUploadDto {

    private String documentName;

    private String operatorId;

    private String knowledgeScopeCode;

    private String knowledgeScopeName;

    private String businessCategory;

    private String documentTags;
}
