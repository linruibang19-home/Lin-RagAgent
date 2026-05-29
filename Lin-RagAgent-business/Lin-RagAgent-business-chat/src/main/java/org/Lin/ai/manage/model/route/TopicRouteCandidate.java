package org.Lin.ai.manage.model.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 模型对象
 * @author: Lin-RAGAgent
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicRouteCandidate {

    private String topicCode;

    private String topicName;

    private String scopeCode;

    private BigDecimal score;

    private String reason;
}
