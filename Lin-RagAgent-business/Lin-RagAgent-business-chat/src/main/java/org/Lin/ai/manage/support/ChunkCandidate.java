package org.Lin.ai.manage.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 支撑组件
 * @author: Lin-RAGAgent
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChunkCandidate {

    private String sectionPath;

    private Long structureNodeId;

    private Integer structureNodeType;

    private String canonicalPath;

    private Integer itemIndex;

    private String text;

    private Integer sourceType;

    public ChunkCandidate(String sectionPath, String text, Integer sourceType) {
        this(sectionPath, null, null, "", null, text, sourceType);
    }
}
