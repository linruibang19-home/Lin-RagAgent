package org.Lin.ai.chatagent.dto;

import lombok.Data;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 数据传输对象
 * @author: Lin-RAGAgent
 **/

@Data
public class ConversationSessionListQueryDto {

    private String keyword;

    private String chatMode;

    private String turnStatus;

    private String pageNo;

    private String pageSize;
}
