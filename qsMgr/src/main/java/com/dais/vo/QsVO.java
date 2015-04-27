package com.dais.vo;

import java.math.BigDecimal;

public class QsVO {
	private Integer id;
	
	private String name;
	
	private Integer totalNum;
	
	private Integer readyNum;
	
	private BigDecimal price;
	
	private Character status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getReadyNum() {
		return readyNum;
	}

	public void setReadyNum(Integer readyNum) {
		this.readyNum = readyNum;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}
	
}
