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
public class DocumentChunkDetailVo {

    private Long documentId;

    private Long taskId;

    private Long planId;

    private DocumentChunkItemVo chunk;

    private DocumentParentBlockItemVo parentBlock;

    private List<DocumentChunkItemVo> siblingChunks;
}
