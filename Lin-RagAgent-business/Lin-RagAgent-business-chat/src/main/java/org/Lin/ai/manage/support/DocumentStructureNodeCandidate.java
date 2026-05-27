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
public class DocumentStructureNodeCandidate {

    private Integer nodeNo;

    private Integer nodeType;

    private Integer parentNodeNo;

    private Integer prevSiblingNodeNo;

    private Integer nextSiblingNodeNo;

    private Integer depth;

    private String nodeCode;

    private String title;

    private String anchorText;

    private String canonicalPath;

    private String sectionPath;

    private String contentText;

    private Integer itemIndex;
}
