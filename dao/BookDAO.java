package com.benjamin.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.benjamin.lms.pojo.Author;
import com.benjamin.lms.pojo.Book;
import com.benjamin.lms.pojo.Branch;
import com.benjamin.lms.pojo.Genre;
import com.benjamin.lms.pojo.Publisher;

public class BookDAO extends BaseDAO {

	public BookDAO(Connection conn) {
		super(conn);
	}

	
	public void addBook(Book book, Publisher publisher) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book (title, pubId) values (?, ?)", new Object[] { book.getTitle(), publisher.getPublisherId() });
	}
	
	public void updateBook(Book book, Publisher publisher) throws ClassNotFoundException, SQLException {
		save("update tbl_book set title" + "=?, pubId"+ "=? where bookId = ?", new Object[] {book.getTitle(), publisher.getPublisherId(), book.getBookId()});
	}
	
	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book where bookId = ?", new Object[] {book.getBookId()});
	}
	
	public List<Book> readBooks() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book", null);
	}
	public void deleteBookAuthors(Book book) throws ClassNotFoundException, SQLException{
		save("delete from tbl_book_authors where bookId = ?", new Object[] {book.getBookId()});
	}
	
	public void insertBookGenre(Book book, Genre genre) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_genres (genre_id, bookId) values (?,?)", new Object[] {genre.getGenreId(), book.getBookId()} );
	}
	
	@Override
	
		public List<Book> extractData(ResultSet rs) throws SQLException {
			List<Book> books = new ArrayList<>();
			while(rs.next()){
				Book a = new Book();
				a.setBookId(rs.getInt("bookId"));
				a.setTitle(rs.getString("title"));
				books.add(a);
			}
			return books;
		}
	

}

