package org.Lin.ai.handler.impl;

import org.Lin.ai.handler.ReaderHandler;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
public class TextReaderHandler implements ReaderHandler {

    @Override
    public boolean canHandle(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".txt") || name.endsWith(".log");
    }

    @Override
    public List<Document> readhandle(File file) throws IOException {
        Resource resource = new FileSystemResource(file);
        return new TextReader(resource).get();
    }
}