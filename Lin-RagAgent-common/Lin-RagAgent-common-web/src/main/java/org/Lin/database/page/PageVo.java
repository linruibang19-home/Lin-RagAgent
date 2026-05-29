package org.Lin.database.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 视图对象
 * @author: Lin-RAGAgent
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long pageNum;

    private long pageSize;

    private long totalSize;

    private List<T> list;
}
