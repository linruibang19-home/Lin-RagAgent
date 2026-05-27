package org.Lin.graphrag.dto;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 数据传输对象
 * @author: Lin-RAGAgent
 **/
public record InstructorCoursesDto(String instructor, List<String> otherCourses) {
}
