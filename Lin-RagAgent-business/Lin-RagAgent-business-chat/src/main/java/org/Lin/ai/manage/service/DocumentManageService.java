package org.Lin.ai.manage.service;

import org.Lin.ai.manage.dto.DocumentIndexBuildDto;
import org.Lin.ai.manage.dto.DocumentChunkQueryDto;
import org.Lin.ai.manage.dto.DocumentChunkDetailQueryDto;
import org.Lin.ai.manage.dto.DocumentDetailQueryDto;
import org.Lin.ai.manage.dto.DocumentDeleteDto;
import org.Lin.ai.manage.dto.DocumentPageQueryDto;
import org.Lin.ai.manage.dto.DocumentStrategyConfirmDto;
import org.Lin.ai.manage.dto.DocumentStrategyPlanQueryDto;
import org.Lin.ai.manage.dto.DocumentTaskLogQueryDto;
import org.Lin.ai.manage.dto.DocumentUploadDto;
import org.Lin.ai.manage.vo.DocumentIndexBuildVo;
import org.Lin.ai.manage.vo.DocumentChunkQueryVo;
import org.Lin.ai.manage.vo.DocumentChunkDetailVo;
import org.Lin.ai.manage.vo.DocumentDeleteVo;
import org.Lin.ai.manage.vo.DocumentListItemVo;
import org.Lin.ai.manage.vo.DocumentPageQueryVo;
import org.Lin.ai.manage.vo.DocumentStrategyConfirmVo;
import org.Lin.ai.manage.vo.DocumentStrategyPlanQueryVo;
import org.Lin.ai.manage.vo.DocumentTaskLogQueryVo;
import org.Lin.ai.manage.vo.DocumentUploadVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 服务层
 * @author: Lin-RAGAgent
 **/

public interface DocumentManageService {

    DocumentUploadVo upload(MultipartFile file, DocumentUploadDto dto);

    DocumentPageQueryVo queryDocumentPage(DocumentPageQueryDto dto);

    DocumentListItemVo queryDocumentDetail(DocumentDetailQueryDto dto);

    DocumentDeleteVo deleteDocument(DocumentDeleteDto dto);

    DocumentStrategyPlanQueryVo queryStrategyPlan(DocumentStrategyPlanQueryDto dto);

    DocumentStrategyConfirmVo confirmStrategy(DocumentStrategyConfirmDto dto);

    DocumentIndexBuildVo buildIndex(DocumentIndexBuildDto dto);

    DocumentChunkQueryVo queryDocumentChunks(DocumentChunkQueryDto dto);

    DocumentChunkDetailVo queryDocumentChunkDetail(DocumentChunkDetailQueryDto dto);

    DocumentTaskLogQueryVo queryTaskLogs(DocumentTaskLogQueryDto dto);
}
