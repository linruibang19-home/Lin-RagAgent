package org.Lin.ai.chatagent.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 数据传输对象
 * @author: Lin-RAGAgent
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDto {

    @NotBlank(message = "question 不能为空")
    private String question;
    private String conversationId;

    @NotBlank(message = "chatMode 不能为空")
    private String chatMode;

    private String selectedDocumentId;
}
