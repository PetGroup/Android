package com.ruyicai.model;

import java.io.Serializable;

public class RequestResultsBean implements Serializable{
	private static final long serialVersionUID = -8704930601795903613L;
	private String error_code = "";
	private String message = "";
	private String result = "";
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
