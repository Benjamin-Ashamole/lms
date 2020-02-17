package com.benjamin.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.benjamin.lms.pojo.Author;
import com.benjamin.lms.pojo.Book;
import com.benjamin.lms.pojo.Publisher;

public class AuthorDAO extends BaseDAO{
	

	

		public AuthorDAO(Connection conn) {
			super(conn);
		}

		public void addAuthor(Author author) throws ClassNotFoundException, SQLException {
			save("insert into tbl_author (authorName) values (?)", new Object[] { author.getAuthorName() });
		}
		
//		public Integer addAuthorPKR(Author author) throws ClassNotFoundException, SQLException {
//			return saveReturnPk("insert into tbl_author (authorName) values (?)", new Object[] {author.getAuthorName()});
//		}

		public void updateAuthor(Author author) throws SQLException, ClassNotFoundException {
			save("update tbl_author set authorName" + "=? where authorId = ?", new Object[]{author.getAuthorName(),author.getAuthorId()} );
		}

		public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
			save("delete from tbl_author where authorId = ?", new Object[] {author.getAuthorId()});
		}
		
		public void insertBookAuthors(Author author, Book book) throws ClassNotFoundException, SQLException{
			save("insert into tbl_book_authors (authorId, bookId) values(?, ?)", new Object[] {author.getAuthorId(), book.getBookId()});
		}
		
		public void deleteBookAuthors(Author author) throws ClassNotFoundException, SQLException{
			save("delete from tbl_book_authors where authorId = ?", new Object[] {author.getAuthorId()});
		}
		
		public List<Author> readAuthors() throws ClassNotFoundException, SQLException {
			return read("select * from tbl_author", null);
		}
		
		public List<Author> readAuthorsByName(String authorName) throws ClassNotFoundException, SQLException {
			return read("select * from tbl_authors where authorName = ?", new Object[]{authorName});
		}

		@Override
		public List<Author> extractData(ResultSet rs) throws SQLException {
			List<Author> authors = new ArrayList<>();
			while(rs.next()){
				Author a = new Author();
				a.setAuthorId(rs.getInt("authorId"));
				a.setAuthorName(rs.getString("authorName"));
				authors.add(a);
			}
			return authors;
		}

	


}
