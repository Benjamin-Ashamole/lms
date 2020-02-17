package com.benjamin.lms.pojo;

import java.io.Serializable;

public class Copies implements Serializable {
	
	
	private static final long serialVersionUID = -7610597069400650699L;
	private Integer bookId;
	private Integer branchId;
	private Integer copies;
	
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public Integer getCopies() {
		return copies;
	}
	public void setCopies(Integer copies) {
		this.copies = copies;
	}

}
