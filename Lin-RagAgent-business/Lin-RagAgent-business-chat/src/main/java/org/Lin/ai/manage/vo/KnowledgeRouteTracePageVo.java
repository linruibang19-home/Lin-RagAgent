package org.Lin.ai.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 视图对象
 * @author: Lin-RAGAgent
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeRouteTracePageVo {

    private String pageNo;

    private String pageSize;

    private String totalSize;

    private String totalPages;

    private List<KnowledgeRouteTraceItemVo> records;
}
