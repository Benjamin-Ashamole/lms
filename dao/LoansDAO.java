package com.benjamin.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.benjamin.lms.pojo.Author;
import com.benjamin.lms.pojo.Borrower;
import com.benjamin.lms.pojo.Loans;

public class LoansDAO extends BaseDAO {
	
	public LoansDAO(Connection conn) {
		super(conn);
		
	}
	
	public void addLoan(Loans loan) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate, dateIn) values (?,?,?,?,?,?)", new Object[] {loan.getBookId(), loan.getBranchId(), loan.getCardNo(), loan.getDateOut(), loan.getDueDate(), loan.getDateIn()} );
	}
	
	public void updateLoan(Loans loan) throws ClassNotFoundException, SQLException {
		save("update tbl_book_loans set dueDate = ?, dateIn = ? where cardNo = ? and bookId = ? and branchId = ?", new Object[] {loan.getDueDate(), loan.getDateIn(), loan.getCardNo(), loan.getBookId(), loan.getBranchId()});
	}
	
	public void deleteLoan(Loans loan) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book_loans where bookId = ?", new Object[] {loan.getBookId()});
	}
	
	public List<Loans> readLoans() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_loans", null);
	}
	
	public List<Loans> readLoansForReturn(Integer cardNo) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_loans where cardNo = ?", new Object[] {cardNo});
	}
	
	public List<Loans> readLoanForUpdate(Loans loan) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_loans where cardNo = ?", new Object[] {loan.getCardNo()});
	}
	@Override
	List extractData(ResultSet rs) throws SQLException {
		ArrayList<Loans> loans = new ArrayList<>();
		System.out.println(Arrays.toString(loans.toArray()));
		while(rs.next()){
			Loans l = new Loans();
			l.setCardNo(rs.getInt("cardNo"));
			l.setBookId(rs.getInt("bookId"));
			l.setBranchId(rs.getInt("branchId"));
			l.setDateOut(rs.getDate("dateOut").toLocalDate());
			l.setDueDate(rs.getDate("dueDate").toLocalDate());
			//l.setDateIn(rs.getDate("dateIn").toLocalDate());
			loans.add(l);
		}
		return loans;
	}

}
