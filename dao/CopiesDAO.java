package com.benjamin.lms.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.benjamin.lms.pojo.Author;
import com.benjamin.lms.pojo.Branch;
import com.benjamin.lms.pojo.Copies;
import com.benjamin.lms.pojo.Loans;

public class CopiesDAO extends BaseDAO{
	
	public CopiesDAO(Connection conn) {
		super(conn);
	
	}
	
	public void addCopy(Copies copies) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_copies (bookId, branchId, noOfCopies) values (?, ?, ?)", new Object[] {copies.getBookId(), copies.getBranchId(), copies.getCopies()});
	}
	
	public void updateCopiesAfterCheckOut(Copies copies) throws ClassNotFoundException, SQLException {
		save("update tbl_book_copies set noOfCopies = ? where branchId = ? and bookId = ?", new Object[] {copies.getCopies() - 1, copies.getBranchId(), copies.getBookId()});
	}
	
	public void updateCopiesAfterCheckIn(Copies copies) throws ClassNotFoundException, SQLException {
		save("update tbl_book_copies set noOfCopies = ? where branchId = ? and bookId = ?", new Object[] {copies.getCopies() + 1, copies.getBranchId(), copies.getBookId()});
	}
	
	public List<Copies> readCopies() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_copies", null);
	}
	
	public List<Copies> readCopiesForUpdate(Integer branch) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_copies where branchId = ?", new Object[] {branch});
	}
	
	
	public List<Copies> readCopiesForAdd(Loans loan) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_copies where branchId = ? and bookId = ?", new Object[] {loan.getBranchId(), loan.getBookId()});
	}

	@Override
	List extractData(ResultSet rs) throws SQLException {
		List<Copies> copies = new ArrayList<>();
		while(rs.next()){
			Copies c = new Copies();
			c.setBookId(rs.getInt("bookId"));
			c.setBranchId(rs.getInt("branchId"));
			c.setCopies(rs.getInt("noOfCopies"));
			copies.add(c);
		}
		return copies;
	}


}
