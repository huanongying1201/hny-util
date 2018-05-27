package com.zjl.exception;

/**
 * 异常类
 * @author zhoujialin
 * @created 2018年2月2日
 */
public class MyException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyException(String message) {
		super(message);
	}

}
