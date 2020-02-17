package com.benjamin.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.benjamin.lms.pojo.Author;
import com.benjamin.lms.pojo.Borrower;
import com.benjamin.lms.pojo.Publisher;

public class BorrowerDAO extends BaseDAO{

	public BorrowerDAO(Connection conn) {
		super(conn);
	}
	
	public void addBorrower(Borrower bow) throws ClassNotFoundException, SQLException {
		save("insert into tbl_borrower (name, address, phone) values (?,?,?)", new Object[] {bow.getBorrowerName(), bow.getBorrowerAddress(),bow.getBorrowerPhone()});
	}
	
	public void updateBorrower(Borrower bow) throws ClassNotFoundException, SQLException {
		save("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?", new Object[] {bow.getBorrowerName(), bow.getBorrowerAddress(),bow.getBorrowerPhone(), bow.getCardNo()});
	}
	
	public void deleteBorrower(Borrower bow) throws ClassNotFoundException, SQLException {
		save("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?", new Object[] {bow.getBorrowerName(), bow.getBorrowerAddress(),bow.getBorrowerPhone(), bow.getCardNo()});
	}
	
	public List<Borrower> readBorrower() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_borrower", null);
	}
	
	public List<Borrower> readBorrowerCardNo(Borrower bow) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_borrower where cardNo = ?", new Object[] {bow.getCardNo()});
	}
	
	
	
	@Override
	ArrayList<Borrower> extractData(ResultSet rs) throws SQLException {
		ArrayList<Borrower> borrowers = new ArrayList<>();
		while(rs.next()){
			Borrower b = new Borrower();
			b.setCardNo(rs.getInt("cardNo"));
			b.setBorrowerName(rs.getString("name"));
			b.setBorrowerAddress(rs.getString("address"));
			b.setBorrowerPhone(rs.getString("phone"));
			borrowers.add(b);
		}
		return borrowers;
	}
}
