package com.csjbot;

/**
 *
 * @Title: DefinedException.java
 * @Project: csjbot-code-generator
 * @Package: com.csjbot
 * @Description: 自定义的系统异常
 * @Company: 苏州穿山甲机器人股份有限公司
 * @author: 钟磊
 * @date: 2018年3月28日 下午12:51:00
 * @version: V1.0.0
 */
public class DefinedException extends RuntimeException {

	private static final long serialVersionUID = -575720902926355714L;

	public DefinedException() {
		super();
	}

	public DefinedException(String message) {
		super(message);
	}

	public DefinedException(String message, Throwable cause) {
		super(message, cause);
	}

	public DefinedException(Throwable cause) {
		super(cause);
	}

}
