package com.dais.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_job_record")
public class JobRecord extends BaseEntity{
	
	@ManyToOne @JoinColumn(updatable=true)
	private Qs qs;
	
	@Column(name="ip")
	private String ip;
	
	@Column(name="job_number")
	private String jobNumber;
	
	@Column(name="img")
	private String img;
	
	//临时保存 Qs id
	private Integer qsId;
	
	
	public JobRecord() {
	}
	public JobRecord(Integer id) {
		super.setId(id);
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Qs getQs() {
		return qs;
	}
	public void setQs(Qs qs) {
		this.qs = qs;
	}
	public Integer getQsId() {
		return qsId;
	}
	public void setQsId(Integer qsId) {
		this.qsId = qsId;
	}
}
