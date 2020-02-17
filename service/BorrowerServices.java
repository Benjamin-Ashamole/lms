package com.benjamin.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
//import java.sql.Date;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.benjamin.lms.dao.AuthorDAO;
import com.benjamin.lms.dao.BookDAO;
import com.benjamin.lms.dao.BorrowerDAO;
import com.benjamin.lms.dao.BranchDAO;
import com.benjamin.lms.dao.CopiesDAO;
import com.benjamin.lms.dao.LoansDAO;
import com.benjamin.lms.pojo.Author;
import com.benjamin.lms.pojo.Book;
import com.benjamin.lms.pojo.Borrower;
import com.benjamin.lms.pojo.Branch;
import com.benjamin.lms.pojo.Copies;
import com.benjamin.lms.pojo.Loans;



public class BorrowerServices extends UtilityClass {

	Main main = new Main();
	public ConnectionUtil connUtil = new ConnectionUtil();
	
	public static void main(String[] args) {
		BorrowerServices bs = new BorrowerServices();
		bs.displayMenu(9);
	}

	
	public void displayMenu(Integer cardNo) {
		
		int action;
		
		LocalDate date = LocalDate.now();
		List<Book> books = new ArrayList<Book>();
		List<Book> availableBooks = new ArrayList<Book>();
		Scanner userSelection = new Scanner(System.in);
		
        System.out.println("1. Check out a Book");
        System.out.println("2. Return a Book");
        System.out.println("3. Quit to Previous Menu");
        
        action = read_range(userSelection, 1,3);
        
        if (action == 3) {
        	main.displayMenu();
        }
        else if (action == 1) {
        	Integer nxtAction;
        	Integer branchId = null;
        	Loans loan = new Loans();
        	Copies copy = new Copies();
        	List<Branch> brList = new ArrayList<Branch>();
    		List<Copies> copies = new ArrayList<Copies>();
    		List<Copies> validCopies = new ArrayList<Copies>();
    		List<Integer> bookCopies = new ArrayList<Integer>();
        	
        	System.out.println("Choose Branch");
        	try {
        		
        		
		
				brList = getAllBranches();
				books = getAllBooks();
				copies = getAllCopies();
				
				String listOfBranches = branchOptions(brList);
				Integer num = branchOptionsInt(brList);
				
				System.out.println(listOfBranches);
				
				nxtAction = read_range(userSelection, 1, num);
				
				for (int i = 0; i < brList.size(); i++) {
					if (nxtAction == brList.get(i).getBranchId()) {
						
						branchId = brList.get(i).getBranchId();
					}
				}
				
				for (int i=0; i < copies.size(); i++) {
					if (copies.get(i).getBranchId() == branchId ) {
						
						bookCopies.add(copies.get(i).getBookId());
						validCopies.add(copies.get(i));
					}
		
				}
				
				for (int i = 0; i < bookCopies.size(); i++) {
					for (int j = 0; j < books.size(); j++) {
						if (bookCopies.get(i) == books.get(j).getBookId()) {
							availableBooks.add(books.get(j));
						}
					}
				}
				System.out.println("Choose Book to CheckOut");
				
				String listOfBooks = bookOptions(availableBooks);
				Integer bknum = bookOptionsInt(availableBooks);
				
				System.out.println(listOfBooks);
				
				Integer Id = read_range(userSelection, 1, bknum); 
				Integer bkId = availableBooks.get(Id-1).getBookId();
				
				for (int i=0; i < validCopies.size(); i++) {
					if (validCopies.get(i).getBookId() == bkId) {
						
						copy = validCopies.get(i);
					}
		
				}
				
				loan.setBookId(bkId);
				loan.setBranchId(branchId);
				loan.setCardNo(cardNo);
				loan.setDateOut(date);
				loan.setDueDate(date.plusDays(7));
				loan.setDateIn(null);
				
				copy.setBookId(bkId);
				copy.setBranchId(branchId);
				
				try {
					newLoan(loan); //This may throw an exception if youre making a duplicate loan.
				} catch (SQLIntegrityConstraintViolationException e) {
					System.out.println("Something Went Wrong with the loan");
				}
				updateCopiesAfterLoan(copy);
				
			} catch (SQLException e) {

				System.out.println("Something went Wrong");
			}
        	finally {
        		System.out.println("Book was checked out\n");
        		main.displayMenu();
        	}
            
        }
//==============================================RETURN BOOK=====================================================       
        if (action == 2) {
        	Integer nxtAction;
        	Integer branchId;
        	List<Loans> loans = new ArrayList<Loans>();
        	List<Branch> branches = new ArrayList<Branch>();
        	List<Loans> itLoan = new ArrayList<Loans>();
        	List<Book> loanBooks = new ArrayList<Book>();
        	List<Copies> copies = new ArrayList<Copies>();
    		List<Copies> validCopies = new ArrayList<Copies>();
        	Loans loan = new Loans();
        	Copies copy = new Copies();
        	
        	try {
        			books = getAllBooks();
        			loans = getAllLoansForReturn(cardNo);
        			branches = getAllBranches();
        			copies = getAllCopies();
        			
        			String listOfBranches = branchOptions(branches);
    				Integer num = branchOptionsInt(branches);
    				
    				System.out.println("Enter Branch to return book");
    				System.out.println(listOfBranches);
    				
    				nxtAction = read_range(userSelection, 1, num);
        			
        			branchId = branches.get(nxtAction - 1).getBranchId();
        			
        			
        			for (int i = 0; i < loans.size(); i++) {
        				if (loans.get(i).getBranchId() == branchId ) {
        					itLoan.add(loans.get(i));
        				}
        			}
        			
        			for (int i=0; i < copies.size(); i++) {
    					if (copies.get(i).getBranchId() == branchId ) {
    						validCopies.add(copies.get(i));
    					}
    		
    				}
        			
        			for (int i = 0; i < itLoan.size(); i++) {
        				for (int j = 0; j < books.size(); j++) {
        					if (itLoan.get(i).getBookId() == books.get(j).getBookId()) {
        						loanBooks.add(books.get(j));
        					}
        				}
        			}
        			
        			// Check the above loop. loanBooks might be empty
        			
        			String listOfBooks = bookOptions(loanBooks);
    				num = bookOptionsInt(loanBooks);
				
					System.out.println("What book would you like to return"); 
					System.out.println(listOfBooks);
				
					Integer Id = read_range(userSelection, 1, num); 
				
					Integer bookId = loanBooks.get(Id -1).getBookId();
					
					for (int i = 0; i < itLoan.size(); i++) {
						if (itLoan.get(i).getBookId() == bookId) {
							loan = itLoan.get(i);
						}
					}
					
					for (int i=0; i < validCopies.size(); i++) {
						if (validCopies.get(i).getBookId() == bookId) {
							
							copy = validCopies.get(i);
						}
					}
					
					
					 
				String dueDate = getNewDueDate(); 
				LocalDate updatedDueDate = LocalDate.parse(dueDate);
				
				loan.setBookId(bookId);
				loan.setBranchId(branchId);
				loan.setCardNo(cardNo);
				loan.setDueDate(updatedDueDate);
				loan.setDateIn(date);
				
				
				copy.setBookId(bookId);
				copy.setBranchId(branchId);
				
				updateLoan(loan);
				updateCopiesAfterLoan(copy);
			
;			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	finally {
        		System.out.println("Loan has been updated\n");
        		main.displayMenu();
        	}
        }
        
	}
//======================================   BRANCH STUFF  ========================================================
	
	
	public List<Branch> getAllBranches() throws SQLException {
		Connection conn = null;
		List<Branch> b = new ArrayList<Branch>();
	
		try {
			conn = connUtil.getConnection();
			
			BranchDAO brdao = new BranchDAO(conn);
			b = brdao.readBranches();
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException  e) {
			
			conn.rollback();
			
			System.out.println("Something failed with reading Branch");
		} 
		finally {
		conn.close();
		}
		return b;
	}
	
	public String branchOptions(List<Branch> list) {
		int counter = 1;
		String str = null;
		StringBuilder strBuilder = new StringBuilder();
		for (Branch b : list) {
			strBuilder = strBuilder.append(counter+". "+b.getBranchName()+"\n");
			counter++;
		}	
		str = strBuilder.toString();
		return str;
	}
	
	public Integer branchOptionsInt(List<Branch> list) {
		int counter = 0;
		for (@SuppressWarnings("unused") Branch b : list) {
			counter++;
			continue;
		}	
		
		return counter;
	}
//========================================================Books===========================================================
	
	
	
	public List<Book> getAllBooks() throws SQLException{
		Connection conn = null;
		List<Book> b = new ArrayList<Book>();
		try {
			conn = connUtil.getConnection();
			
			BookDAO bdao = new BookDAO(conn);
			b = bdao.readBooks();
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException  e) {
			
			conn.rollback();
			
			System.out.println("Something failed with getting the books");
		} finally {
			conn.close();
		}
		
		return b;
	}
	
	

	
	public String bookOptions(List<Book> list) {
		int counter = 1;
		String str = null;
		StringBuilder strBuilder = new StringBuilder();
		for (Book b : list) {
//			System.out.print(counter+b.getTitle());
			strBuilder = strBuilder.append(counter+". "+b.getTitle()+"\n");
			counter++;
			
		}	
		str = strBuilder.toString();
		return str;
	}
	
	public Integer bookOptionsInt(List<Book> list) {
		int counter = 0;
		for (Book b : list) {
			counter++;
			continue;	
		}	
		
		return counter;
	}
	
	public void newLoan(Loans loan) throws SQLException {
		
		Connection conn = null;
		
		try {
			conn = connUtil.getConnection();
			
			LoansDAO ldao = new LoansDAO(conn);
			ldao.addLoan(loan);
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException  e) {
			
			conn.rollback();
			e.printStackTrace();
			
			System.out.println(">>>Something failed with adding a loan<<<");
		} finally {
			conn.close();
		}
		
	}
	
public void updateLoan(Loans loan) throws SQLException {
		
		Connection conn = null;
		
		try {
			conn = connUtil.getConnection();
			
			LoansDAO ldao = new LoansDAO(conn);
			ldao.updateLoan(loan);
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException  e) {
			
			conn.rollback();
			e.printStackTrace();
			
			System.out.println(">>>Something failed with Updating the loan.<<<");
		} finally {
			conn.close();
		}
		
	}
	
//	
	public List<Loans> getAllLoansForReturn(Integer loan) throws SQLException{
		Connection conn = null;
		List<Loans> c = new ArrayList<Loans>();
		
		try {
			conn = connUtil.getConnection();
			
			LoansDAO loansdao = new LoansDAO(conn);
			c = loansdao.readLoansForReturn(loan);
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException  e) {
			
			conn.rollback();
			
			System.out.println("Something failed with getting loans");
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return c;
		
	}
	
	public String getNewDueDate() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter New Due Date In The Format: Example 2020-02-23:\n");
		String dueDate = sc.nextLine();
		
		return dueDate;
	}
	
	
//================================================Copies===================================================================	
	
	public List<Copies> getAllCopies() throws SQLException {
		Connection conn = null;
		List<Copies> c = new ArrayList<Copies>();

		try {
			conn = connUtil.getConnection();
			
			CopiesDAO copiesdao = new CopiesDAO(conn);
			c = copiesdao.readCopies();
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException  e) {
			
			conn.rollback();
			
			System.out.println("Something failed with reading copies");
		} finally {
			conn.close();
		}
		
		return c;
	}
	
	public void updateCopiesAfterLoan(Copies copies) throws SQLException {
		Connection conn = null;
		
		try {
			conn = connUtil.getConnection();
			CopiesDAO copiesdao = new CopiesDAO(conn);
			
			copiesdao.updateCopiesAfterCheckOut(copies);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		
		
	}
	
	public void updateCopiesAfterReturn(Copies copies) throws SQLException {
		Connection conn = null;
		
		try {
			conn = connUtil.getConnection();
			CopiesDAO copiesdao = new CopiesDAO(conn);
			
			copiesdao.updateCopiesAfterCheckIn(copies);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		
		
	}
	
	
//	public void getBranchCopies(Integer branchId) throws SQLException {
//		
//		Connection conn = null;
//		Copies cop = new Copies();
//		cop.setBranchId(branchId);
//		cop.setBookId(null);
//		cop.setCopies(null);
//		//List<Copies> c = new ArrayList<Copies>();
//
//		try {
//			conn = connUtil.getConnection();
//			
//			CopiesDAO copiesdao = new CopiesDAO(conn);
//			
//			copiesdao.readCopiesForUpdate(branchId);
//			copiesdao.UpdateCopiesAfterCheckOut(cop, branchId);
//			
//			conn.commit();
//		} catch (ClassNotFoundException | SQLException  e) {
//			
//			conn.rollback();
//			
//			System.out.println("Something failed with reading Branch");
//		} finally {
//			conn.close();
//		}
//	}
//
}
