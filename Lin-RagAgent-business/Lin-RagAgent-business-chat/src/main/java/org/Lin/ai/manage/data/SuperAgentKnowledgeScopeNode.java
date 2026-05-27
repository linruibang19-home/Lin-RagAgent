package org.Lin.ai.manage.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.Lin.database.data.BaseTableData;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 数据实体
 * @author: Lin-RAGAgent
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("super_agent_knowledge_scope_node")
@EqualsAndHashCode(callSuper = true)
public class SuperAgentKnowledgeScopeNode extends BaseTableData {

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    private String scopeCode;

    private String scopeName;

    private String parentScopeCode;

    private String description;

    private String aliases;

    private String examples;

    private Integer sortOrder;
}
