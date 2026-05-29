package org.Lin.ai.chatagent.rag.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 检索阶段真正执行的问题计划
 * @author: Lin-RAGAgent
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrievalQuestionPlan {

    private String retrievalQuestion;

    private List<String> subQuestions;
}
