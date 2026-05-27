package org.Lin.ai.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 视图对象
 * @author: Lin-RAGAgent
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentParentBlockItemVo {

    private Long parentBlockId;

    private Integer parentBlockNo;

    private String sectionPath;

    private Integer sourceType;

    private String sourceTypeName;

    private Integer charCount;

    private Integer tokenCount;

    private Integer childCount;

    private Integer startChunkNo;

    private Integer endChunkNo;

    private String parentText;
}
