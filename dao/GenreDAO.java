package com.benjamin.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.benjamin.lms.pojo.Author;
import com.benjamin.lms.pojo.Genre;

public class GenreDAO extends BaseDAO {
	
	public GenreDAO(Connection conn) {
		super(conn);
	}
	
	public void addGenre(Genre genre) throws ClassNotFoundException, SQLException {
		save("insert into tbl_genre (genre_name) values (?)", new Object[] {genre.getGenreName()});
		}
	
	public void updateGenre(Genre genre) throws ClassNotFoundException, SQLException {
		save("update tbl_genre set genre_name"+ "=? where genre_id = ?", new Object[] {genre.getGenreName(), genre.getGenreId()} );
	}
	
	public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException {
		save("delete from tbl_genre where genreId = ?", new Object[] {genre.getGenreId()} );
	}
	
	public List<Genre> readGenre() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_genre", null);
	}

	@Override
	List extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<>();
		while(rs.next()){
			Genre g = new Genre();
			g.setGenreName(rs.getString("genre_name"));
			g.setGenreId(rs.getInt("genre_id"));
			genres.add(g);
		}
		return genres;
	}


}
