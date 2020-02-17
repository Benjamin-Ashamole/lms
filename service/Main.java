package com.benjamin.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import com.benjamin.lms.dao.BorrowerDAO;
import com.benjamin.lms.pojo.Borrower;


public class Main extends UtilityClass {
	
	public ConnectionUtil connUtil = new ConnectionUtil();

	public static void main(String[] args) {
		
		Main main = new Main();
		main.displayMenu();

	}

	public void displayMenu() {
		int action;
		
		Scanner userSelection = new Scanner(System.in);
		
		System.out.println("Choose A Service");
        System.out.println("1. Administration Services");
        System.out.println("2. Librarian Services");
        System.out.println("3. Borrower Services");
        
        action = read_range(userSelection, 1,3);
        
        if (action == 1) {
        	
        	AdministratorService as = new AdministratorService();
        	as.displayMenu();
        
        }
        
        if (action == 2) {
        	
        	LibrarianServices ls = new LibrarianServices();
        	ls.displayMenu();
        	
        	
        }
        
        if (action == 3) {
        	Connection conn = null;
        	BorrowerServices bs = new BorrowerServices();
        	Integer cardNoCheck;
        	Borrower bow = new Borrower();
        	List<Borrower> bowList = new ArrayList<Borrower>();
        	try {
				conn = connUtil.getConnection();
				
	        	BorrowerDAO brdao = new BorrowerDAO(conn);
	        	
	        	System.out.println("Please Enter Your Library Card Number");
	        	cardNoCheck = userSelection.nextInt();
	        	//userSelection.nextLine();  This might prevent you from having to enter it twice
	        	
	        	bow.setCardNo(cardNoCheck);
	        	bowList = brdao.readBorrowerCardNo(bow);
	        	
	        	if (bowList.get(0).getCardNo() ==  cardNoCheck) {
	        		bs.displayMenu(bow.getCardNo());
	        	}
	        	else {
	        		System.out.println("cardNo is invalid");
	        		displayMenu();
	        	}
	        	
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	
        	//Promt the borrower for cardNo'
        	
        	
        	
        	
        	//Check the tbl_borrower to see if that cardNo exists
        	//if it does allow him access to the next Menu else tell him to go fuck himself.
        	
        	
        	
        	
        	
        }
        
        //userSelection.close();
        
	}

}
