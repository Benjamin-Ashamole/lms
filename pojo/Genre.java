package com.benjamin.lms.pojo;

import java.io.Serializable;
import java.util.List;

import com.benjamin.lms.pojo.Book;

public class Genre implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1198231888198775854L;
	private Integer genreId;
	private String genreName;
	
	public Integer getGenreId() {
		return genreId;
	}
	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}
	public String getGenreName() {
		return genreName;
	}
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	private List<Book> books;

}
