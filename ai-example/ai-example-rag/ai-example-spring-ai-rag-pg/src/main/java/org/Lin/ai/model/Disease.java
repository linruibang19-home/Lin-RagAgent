package org.Lin.ai.model;

import lombok.Data;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 模型对象
 * @author: Lin-RAGAgent
 **/
@Data
public class Disease {

    private String id;
    private String name;
    private String symptoms;
    private String treatment;
    private String department;
    private String category;
}
