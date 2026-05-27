package org.Lin.ai.manage.service;

import org.Lin.enums.DocumentFileTypeEnum;
import org.Lin.ai.manage.support.DocumentAnalysisResult;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 服务层
 * @author: Lin-RAGAgent
 **/

public interface DocumentParserService {

    DocumentAnalysisResult parse(byte[] bytes, String originalFileName, String mimeType, DocumentFileTypeEnum fileType);
}
