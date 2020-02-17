package com.benjamin.lms.pojo;

import java.io.Serializable;
import java.util.List;

import com.benjamin.lms.pojo.Book;

public class Publisher implements Serializable {

	
	private static final long serialVersionUID = 8441309873989939884L;
	private Integer publisherId;
	private String publisherName;
	private String publisherAddress;
	private String publisherPhone;
	private List<Book> pubBooks;
	
	public Integer getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public String getPublisherAddress() {
		return publisherAddress;
	}
	public void setPublisherAddress(String publisherAddress) {
		this.publisherAddress = publisherAddress;
	}
	public String getPublisherPhone() {
		return publisherPhone;
	}
	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}
	public List<Book> getPubBooks() {
		return pubBooks;
	}
	public void setPubBooks(List<Book> pubBooks) {
		this.pubBooks = pubBooks;
	}

}
