package org.Lin.ai.manage.model.graph;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 章节及其直接子章节
 * @author: Lin-RAGAgent
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphSectionWithChildren {

    private GraphSection section;

    @Builder.Default
    private List<GraphSection> children = new ArrayList<>();
}
