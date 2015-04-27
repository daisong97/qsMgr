package com.dais.vo;

import java.math.BigDecimal;

public class JrVO {
	private Integer id;
	
	private String ip;
	
	private Integer qsId;
	
	private String qsName;
	
	private String jobNumber;
	
	private Integer readyNum;
	
	private BigDecimal qPrice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Integer getReadyNum() {
		return readyNum;
	}

	public void setReadyNum(Integer readyNum) {
		this.readyNum = readyNum;
	}

	public BigDecimal getqPrice() {
		return qPrice;
	}

	public void setqPrice(BigDecimal qPrice) {
		this.qPrice = qPrice;
	}

	public Integer getQsId() {
		return qsId;
	}

	public void setQsId(Integer qsId) {
		this.qsId = qsId;
	}

	public String getQsName() {
		return qsName;
	}

	public void setQsName(String qsName) {
		this.qsName = qsName;
	}
}