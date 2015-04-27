package com.dais.vo;

public class ResponseStatus{
		String msg;
		Integer status;
		
		public ResponseStatus() {
		}
		public ResponseStatus(String msg, Integer status) {
			super();
			this.msg = msg;
			this.status = status;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
	}