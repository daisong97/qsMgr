package com.dais.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 问卷
 * @author dais
 *
 */
@Entity
@Table(name="t_qs")
public class Qs extends BaseEntity{
	
	/**问卷名称*/
	@Column(name="name")
	private String name;
	
	@Column(name="status")
	private Character status;
	
	/**份数*/
	@Column(name="total_num")
	private Integer totalNum;
	
	/**剩余份数*/
	@Column(name="overplus")
	private Integer overplus;
	
	/**价格*/
	@Column(name="price")
	private BigDecimal price;
	
	public Qs(Integer id) {
		super.setId(id);
	}
	public Qs() {
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

	public Integer getOverplus() {
		return overplus;
	}

	public void setOverplus(Integer overplus) {
		this.overplus = overplus;
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
