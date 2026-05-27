package org.Lin.exception;

import lombok.Data;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 异常类
 * @author: Lin-RAGAgent
 **/

@Data
public class ArgumentError {

	private String argumentName;

	private String message;
}
