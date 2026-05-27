package org.Lin.ai.handler;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 处理器
 * @author: Lin-RAGAgent
 **/
@Component
public class ReaderHandlerContext {

    private final List<ReaderHandler> readerHandlerList;

    public ReaderHandlerContext(List<ReaderHandler> readerHandlerList) {
        this.readerHandlerList = readerHandlerList;
    }

    public List<Document> read(File file) throws IOException {
        ReaderHandler readerHandler = readerHandlerList.stream()
                .filter(handler -> handler.canHandle(file))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("此文件类型不支持，文件类型: " 
                        + file.getName()));
        return readerHandler.readhandle(file);
    }
}
