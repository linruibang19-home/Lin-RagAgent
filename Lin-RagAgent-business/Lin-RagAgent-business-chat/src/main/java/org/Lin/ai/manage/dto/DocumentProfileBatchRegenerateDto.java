package org.Lin.ai.manage.dto;

import lombok.Data;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 数据传输对象
 * @author: Lin-RAGAgent
 **/
@Data
public class DocumentProfileBatchRegenerateDto {

    private List<String> documentIds;

    private String operatorId;
}
