package com.benjamin.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.benjamin.lms.dao.AuthorDAO;
import com.benjamin.lms.dao.BranchDAO;
import com.benjamin.lms.pojo.Author;
import com.benjamin.lms.pojo.Book;
import com.benjamin.lms.pojo.Branch;

public class LibrarianServices extends UtilityClass implements Utility {
	
	Main main = new Main();
	public ConnectionUtil connUtil = new ConnectionUtil();
	
	
	public static void main(String[] args) {
		LibrarianServices ls = new LibrarianServices();
		ls.displayMenu();
	}
	@Override
	public void displayMenu() {
		int action;
		
		Scanner userSelection = new Scanner(System.in);
		
        System.out.println("1. Enter Your Branch");
        System.out.println("2. Quit to Previous Menu");
        
        action = read_range(userSelection, 1,2);
        
        if (action == 2) {
        	main.displayMenu();
        }
        
        
        if (action == 1) {
        	
        	int nextAction;
        	int newAction;
        	
        	System.out.println("Choose Branch");
        	
        	List<Branch> brList = new ArrayList<Branch>();
        	try {
				brList = getAllBranches();
				String listOfBranches = branchOptions(brList);
				Integer num = branchOptionsInt(brList);
				System.out.println(listOfBranches);
				
				nextAction = read_range(userSelection, 1, num);
				
			System.out.println("1. Update Library Details");
			System.out.println("2. Quit to Previous Menu");
			
				newAction = read_range(userSelection, 1,2);
				
				if (newAction == 2) {
					
					displayMenu();
				}
				
				if (newAction == 1) {
					
					String libName = getLibName();
					String libAddy = getLibAddy();
					
					Branch updateBranch = new Branch();
					updateBranch.setBranchName(libName);
					updateBranch.setBranchAddress(libAddy);
					updateBranch.setBranchId(nextAction);
					
					updateBranchInfo(updateBranch);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
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
	
	public String getLibName() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Library Name");
		String libName = sc.nextLine();
		
		return libName;
	}
	
	
	
	public String getLibAddy() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Library Address");
		String libAddy = sc.nextLine();
		
		return libAddy;
	}
	
	public void updateBranchInfo(Branch branch) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BranchDAO brdao = new BranchDAO(conn);
			brdao.updateBranch(branch);
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with update branch");
		} finally {
			conn.close();
		}
	}
	
	
}
