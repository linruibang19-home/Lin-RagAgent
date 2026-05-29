package org.Lin.ai.manage.service;

import org.Lin.ai.manage.support.StoredObjectInfo;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 服务层
 * @author: Lin-RAGAgent
 **/

public interface DocumentStorageService {

    StoredObjectInfo uploadOriginalFile(Long documentId, String originalFileName, byte[] bytes, String contentType);

    String uploadParsedText(Long documentId, String parsedText);

    byte[] downloadObject(String objectName);

    String downloadText(String objectName);

    void deleteObjects(List<String> objectNameList);
}
