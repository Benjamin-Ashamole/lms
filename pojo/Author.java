package com.benjamin.lms.pojo;

import java.io.Serializable;
import java.util.List;

import com.benjamin.lms.pojo.Book;

public class Author implements Serializable {
	
	
	private static final long serialVersionUID = -2807100341709858394L;
	private Integer authorId;
	private String authorName;
	private List<Book> books; //many to many
	
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
