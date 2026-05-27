package org.Lin.ai.manage.model.graph;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 章节及其父章节、相邻兄弟章节
 * @author: Lin-RAGAgent
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphSectionWithSiblings {

    private GraphSection section;

    private GraphSection parent;

    private GraphSection previousSibling;

    private GraphSection nextSibling;
}
