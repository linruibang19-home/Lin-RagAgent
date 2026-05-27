package org.Lin.ai.chatagent.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Lin.ai.chatagent.model.ConversationSessionView;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 视图对象
 * @author: Lin-RAGAgent
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationSessionListVo {

    private long pageNo;

    private long pageSize;

    private long totalSize;

    private long totalPages;

    private List<ConversationSessionView> sessions;
}
