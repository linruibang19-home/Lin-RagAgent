package org.Lin.ai.handler;

import org.springframework.ai.document.Document;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 处理器
 * @author: Lin-RAGAgent
 **/
public interface ReaderHandler {
    /**
     * 是否可以处理该文件
     */
    boolean canHandle(File file);

    /**
     * 读取文件并返回Document列表
     */
    List<Document> readhandle(File file) throws IOException;
}