package com.benjamin.lms.pojo;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable{

	
	private static final long serialVersionUID = 9146031000167407248L;
	private Integer bookId;
	private String title;
	private Integer publisherId;
	private List<Author> authors;
	//list of genres, branches, 
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}
	public List<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

}
