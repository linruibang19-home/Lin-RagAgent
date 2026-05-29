package org.Lin.ai.manage.dto;

import lombok.Data;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 数据传输对象
 * @author: Lin-RAGAgent
 **/
@Data
public class KnowledgeRouteTraceQueryDto {

    private String conversationId;

    private String mode;

    private String routeStatus;

    private String pageNo;

    private String pageSize;
}
