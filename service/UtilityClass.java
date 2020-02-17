package com.benjamin.lms.service;

import java.util.Scanner;

public class UtilityClass {
	
	protected static int read_range(Scanner scanner, int low, int high) {
	    int value;
	    value = scanner.nextInt();
	    while (value < low || value > high) {
	    	
	    	if (value == 0) {
	    		return value;
	    	}
	    	else {
	      System.out.print("Please enter a value between " + low + " and " + high + ": ");
	      value = scanner.nextInt();
	    	}
	    }
	    //scanner.close();
	    return value;
	  }

}
