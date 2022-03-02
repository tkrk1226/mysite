package com.poscoict.mysite.dto;

public class JsonResult {
	private String result; 		/* "success" or "fail" */
	private Object data; 		/* if success, set */
	private String message; 	/* if fail, set */

	private JsonResult() {
		
	}
	
	private JsonResult(String result, Object data) {
		this.result = result;
		this.data = data;
	}
	
	private JsonResult(String result, String message) {
		this.result = result;
		this.message = message;
	}
	
	// Factory Method
	public static JsonResult success(Object data) {
		return new JsonResult("success", data);
	}
	
	public static JsonResult fail(String message) {
		return new JsonResult("fail", message);
	}
	
	// 문제의 소지가 있는 setter들을 다 없애버린다.
	
	public String getResult() {
		return result;
	}
	public Object getData() {
		return data;
	}
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "JsonResult [result=" + result + ", data=" + data + ", message=" + message + "]";
	}
}
