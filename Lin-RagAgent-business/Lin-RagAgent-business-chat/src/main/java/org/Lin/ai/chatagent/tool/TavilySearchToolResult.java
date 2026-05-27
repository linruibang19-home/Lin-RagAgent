package org.Lin.ai.chatagent.tool;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Lin.ai.chatagent.model.SearchReference;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 工具类
 * @author: Lin-RAGAgent
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TavilySearchToolResult {

    private String query;
    private String answer;
    private List<SearchReference> results;
}
