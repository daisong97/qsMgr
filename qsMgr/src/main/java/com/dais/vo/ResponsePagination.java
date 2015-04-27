package com.dais.vo;

import java.util.List;

public class ResponsePagination {
	private List<?> rows;
	
	private Integer total;

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
