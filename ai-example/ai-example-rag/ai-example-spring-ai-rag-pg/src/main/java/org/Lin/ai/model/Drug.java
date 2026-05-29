package org.Lin.ai.model;

import lombok.Data;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 模型对象
 * @author: Lin-RAGAgent
 **/
@Data
public class Drug {

    private String id;
    private String name;
    private String indications;
    private String dosage;
    private String precautions;
    private String category;
}
