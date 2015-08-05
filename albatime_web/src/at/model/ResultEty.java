package at.model;

import lombok.ToString;

@ToString(includeFieldNames = true)
public class ResultEty {
	private Boolean result = true;
	private String errorCode = "NO_ERROR";
	private Object data = "[]";

	public ResultEty() {}

	public ResultEty(Object data) {
		this.data = data;
	}

	public ResultEty(Boolean result, String errorCode) {
		this.result = result;
		this.errorCode = errorCode;
	}
}
