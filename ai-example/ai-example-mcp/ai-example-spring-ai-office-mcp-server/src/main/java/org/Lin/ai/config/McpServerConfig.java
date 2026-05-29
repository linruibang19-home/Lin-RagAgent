package org.Lin.ai.config;


import org.Lin.ai.office.tool.AttendanceTools;
import org.Lin.ai.office.tool.MeetingRoomTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 配置类
 * @author: Lin-RAGAgent
 **/
@Configuration
public class McpServerConfig {

    /**
     * 注册考勤工具
     */
    @Bean
    public ToolCallbackProvider attendanceToolProvider(AttendanceTools attendanceTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(attendanceTools)
                .build();
    }

    /**
     * 注册会议室工具
     */
    @Bean
    public ToolCallbackProvider meetingRoomToolProvider(MeetingRoomTools meetingRoomTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(meetingRoomTools)
                .build();
    }
}