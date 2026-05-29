package org.Lin.ai.manage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 数据传输对象
 * @author: Lin-RAGAgent
 **/

@Data
public class DocumentDeleteDto {

    @NotBlank(message = "文档id不能为空")
    private String documentId;
}
