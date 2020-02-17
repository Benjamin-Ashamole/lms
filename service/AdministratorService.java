package com.benjamin.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.benjamin.lms.dao.AuthorDAO;
import com.benjamin.lms.dao.BookDAO;
import com.benjamin.lms.dao.BranchDAO;
import com.benjamin.lms.dao.CopiesDAO;
import com.benjamin.lms.dao.GenreDAO;
import com.benjamin.lms.dao.LoansDAO;
import com.benjamin.lms.dao.PublisherDao;
import com.benjamin.lms.pojo.Author;
import com.benjamin.lms.pojo.Book;
import com.benjamin.lms.pojo.Branch;
import com.benjamin.lms.pojo.Copies;
import com.benjamin.lms.pojo.Genre;
import com.benjamin.lms.pojo.Loans;
import com.benjamin.lms.pojo.Publisher;



public class AdministratorService extends UtilityClass implements Utility{
	
Main main = new Main();
	
	public ConnectionUtil connUtil = new ConnectionUtil();
	
	public static void main(String[] args) {
		AdministratorService as = new AdministratorService();
		as.displayMenu();
	}
	
	@Override
	public void displayMenu() {
		Integer action;
		BorrowerServices bs = new BorrowerServices();
		Scanner userSelection = new Scanner(System.in);
		
		System.out.println("Services");
		System.out.println("1. Books");
	    System.out.println("2. Author");
	    System.out.println("3. Genre");
	    System.out.println("4. Publisher");
        System.out.println("5. Library Branches");
        System.out.println("6. Borrowers");
        System.out.println("7. Loans");
        System.out.println("8. Copies");
        System.out.println("9. Quit to Previous Menu");
        
        action = read_range(userSelection, 1,9);
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Book <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<      
        if (action == 1) {
        	
        	Integer nextAction;
        	
        	System.out.println("1. Add Book");
    	    System.out.println("2. Update Book");
    	    System.out.println("3. Delete Book");
    	    System.out.println("4. View All Books");
    	    System.out.println("5. Quit to Previous Menu");
    	    
    	    nextAction = read_range(userSelection, 1,5);
    	    
    	    if (nextAction == 5) {
    	    	displayMenu();
    	    }
    	    
    	    if (nextAction == 1) {
    	    	
    	    	Integer branchId;
    	    	Integer nxtAction;
    	    	Integer bookId;
    	    	List<Branch> branches = new ArrayList<Branch>();
    	    	List<Copies> copies = new ArrayList<Copies>();
    	    	List<Book> books = new ArrayList<Book>();
    	    	List<Author> authors = new ArrayList<Author>();
    	    	Book book = new Book();
    	    	Genre genre = new Genre();
    	    	
				try {
					branches = bs.getAllBranches();
					books = bs.getAllBooks();
					//System.out.println(Arrays.toString(books.toArray()));
					String listOfBooks = bs.bookOptions(books);
					Integer num = bs.bookOptionsInt(books);
					System.out.println("Enter the key of book you wish to add.\n Enter 0 if book is not shown\n");
					System.out.println(listOfBooks);
					nxtAction = read_range(userSelection, 1, num);
					
					
					if (nxtAction == 0){
						
						String bookTitle = getBookTitle();
						book = getBookPublisher();
						authors = getBooksAuthors();
						genre = getBookGenre();
						
					}
					
					if (nxtAction > 0) {
						bookId = books.get(nxtAction - 1).getBookId();
						
						String listOfBranches = bs.branchOptions(branches);
						num = bs.branchOptionsInt(branches);
						System.out.println("What Branch would you like to add the book");
						System.out.println(listOfBranches);
						nxtAction = read_range(userSelection, 1, num);
						branchId = branches.get(nxtAction - 1).getBranchId();
						
						
						
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					System.out.println("Copy was added to the branch");
					displayMenu();
				}
    	    }
    	    
    	    if (nextAction == 2) {
    	    	Integer nxtAction;
    	    	Integer bookId;
    	    	Integer pubId;
    	    	Book book = new Book();
    	    	Publisher pub = new Publisher();
    	    	List<Book> books = new ArrayList<Book>();
    	    	List<Publisher> publishers = new ArrayList<Publisher>();
//    	    	System.out.println("What will you like to edit\n"+"1. Book Title\n"+"Book Publisher\n"+"Both");
    	    	
    	    		try {
						books = bs.getAllBooks();
						publishers = getAllPublishers();
						String listOfBooks = bs.bookOptions(books);
						Integer num = bs.bookOptionsInt(books);
						System.out.println("Enter the key of book you wish to edit.");
						System.out.println(listOfBooks);
						nxtAction = read_range(userSelection, 1, num);
						bookId = books.get(nxtAction - 1).getBookId();
						
						
							String bookTitle = getBookTitle();
							book.setTitle(bookTitle);
							book.setBookId(bookId);
							System.out.println("Choose Publisher");
							String listOfPubs = publisherOptions(publishers);
							num = publisherOptionsInt(publishers);
							System.out.println(listOfPubs);
							nxtAction = read_range(userSelection, 1, num);
							pubId = publishers.get(nxtAction - 1).getPublisherId();
							pub.setPublisherId(pubId);
						
						updateBook(book, pub);
						
						
						
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						System.out.println("Something went wrong with updating books"); 
						e.printStackTrace();
					} finally {
						System.out.println("Book Updated\n");
						displayMenu();
					}
    	    }
    	    
    	    if (nextAction == 3) {
    	    	Integer nxtAction;
    	    	Integer bookId;
    	    	Book book = new Book();
    	    	List<Book> books = new ArrayList<Book>();
    	    	
    	    	try {
					books = bs.getAllBooks();
					String listOfBooks = bs.bookOptions(books);
					Integer num = bs.bookOptionsInt(books);
					System.out.println("Enter the key of book you wish to delete.");
					System.out.println(listOfBooks);
					nxtAction = read_range(userSelection, 1, num);
					bookId = books.get(nxtAction - 1).getBookId();
					
					book.setBookId(bookId);
					deletingBook(book);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Something went wrong with deleting books"); 
					e.printStackTrace();
				} finally {
					System.out.println("Book Deleted\n");
					displayMenu();
				}
    	    	
    	    	
    	    }
    	    
    	    if (nextAction == 4) {
    	    	
    	    	List<Book> books;
				try {
					books = bs.getAllBooks();

	    	    	String listOfBooks = bs.bookOptions(books);
	    	    	System.out.println("Books:"); 
					System.out.println(listOfBooks);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Something went wrong with viewing books"); 
				}
    	    	
    	    	
    	    }
        	
        }
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Author <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<              
        else if (action == 2) {
        	Integer nextAction;
        	Author author = new Author();
        	System.out.println("1. Add Author");
    	    System.out.println("2. Update Author");
    	    System.out.println("3. Delete Author");
    	    System.out.println("4. View All Authors");
    	    System.out.println("5. Quit to Previous Menu");
    	    
    	    nextAction = read_range(userSelection, 1,5);
    	    
    	    if (nextAction == 5) {
    	    	displayMenu();
    	    }
    	    
    	    if (nextAction == 1) {
    	    	Integer nxtAction;
    	    	List<Book> books = new ArrayList<Book>();
    	    	List<Book> authorsBooks = new ArrayList<Book>();
    	    	
    	    	
    	    	String authorName = getAuthorName();
    	    	author.setAuthorName(authorName);
    	    	
    	    	try {
    	    		 
					books = bs.getAllBooks();
					String listOfBooks = bs.bookOptions(books);
					Integer num = bs.bookOptionsInt(books);
					System.out.println("Enter The Key Of Book(s) You Wish To Add.\n Or Enter '0' to Skip Adding Any Books\n");
					System.out.println(listOfBooks);
					nxtAction = read_range(userSelection, 1, num);
					
					if (nxtAction == 0) {
						saveAuthor(author);
						System.out.print("Author Added");
						
					} else {
						saveAuthor(author);
						List<Author> authors = new ArrayList<Author>();
						authors = getAllAuthors();
						Integer authorId = authors.get((authors.size() - 1)).getAuthorId();
					while (nxtAction != 0) {
							
						authorsBooks.add(books.get(nxtAction - 1));
						System.out.println("Enter The Key Of Book(s) You Wish To Add.\n Enter '0' When You're Done Adding Authors\n");
						System.out.println(listOfBooks); //add check here to make sure youre not adding the same Book objects over and over
						nxtAction = read_range(userSelection, 1, num);		
							
					}
					System.out.println(Arrays.toString(authorsBooks.toArray()));
					System.out.println(authorsBooks.get(0).getBookId());
					
					author.setBooks(authorsBooks);
					author.setAuthorId(authorId);
					saveAuthorWithBooks(author);
					}	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Something Went Wrong with Adding Author");
				} finally {
					displayMenu();
				}
    	    	
    	    }
    	    
    	    if (nextAction == 2) {
    	    	Integer nxtAction;
    	    	Integer authorId;
    	    	List<Author> authors = new ArrayList<Author>();
				try {
					authors = getAllAuthors();
					String listOfAuthors = authorOptions(authors);
					Integer num = authorOptionsInt(authors);
					System.out.println(listOfAuthors);
					nxtAction = read_range(userSelection, 1, num);
					
					authorId = authors.get(nxtAction - 1).getAuthorId();
					String authorName = getAuthorName();
					
					author.setAuthorName(authorName);
					author.setAuthorId(authorId);
					updatingAuthor(author);
					
					
				} catch (SQLException e) {
					System.out.println("Author Updated");
					e.printStackTrace();
				} finally {
					displayMenu();
				}
    	    }
    	    
    	    if (nextAction == 3) {
    	    	Integer nxtAction;
    	    	Integer authorId;
    	    	List<Author> authors = new ArrayList<Author>();
    	    	try {
					authors = getAllAuthors();
					String listOfAuthors = authorOptions(authors);
					Integer num = authorOptionsInt(authors);
					System.out.println(listOfAuthors);
					nxtAction = read_range(userSelection, 1, num);
					
					authorId = authors.get(nxtAction - 1).getAuthorId();
					
					author.setAuthorId(authorId);
					deletingAuthor(author);
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					System.out.println("Author Deleted"); 
					displayMenu();
				}
    	    }
    	    
    	    if (nextAction == 4) {
    	    	List<Author> authors = new ArrayList<Author>();
    	    	
    	    	try {
					authors = getAllAuthors();
					String listOfAuthors = authorOptions(authors);
					Integer num = authorOptionsInt(authors);
					System.out.println(listOfAuthors);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    	
    	    }
        }
        
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Genre <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        else if (action == 3) {
        	
        	Integer nextAction;
        	
        	System.out.println("1. Add Genre");
    	    System.out.println("2. Update Genre");
    	    System.out.println("3. Delete Genre");
    	    System.out.println("4. View All Genre's");
    	    System.out.println("5. Quit to Previous Menu");
    	    
    	    nextAction = read_range(userSelection, 1,5);
    	    
    	    if (nextAction == 5) {
    	    	displayMenu();
    	    }
    	    
    	    if (nextAction == 1) {
    	    	
    	    	try {
					saveGenre(newGenre());
					System.out.println("Genre has been added");
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Sommething wrong with adding genre");
					e.printStackTrace();
				}
    	    }
    	    
    	    if (nextAction == 2) {
    	    	
    	    	Integer nxtAction;
    	    	List<Genre> genres = new ArrayList<Genre>();
    	    	Integer genreId;
    	    	Genre updatedGenre;
    	    	
    	    	
    	    	try {
    	    		
    	    		genres = getAllGenre();
					String listOfGenres = genreOptions(genres);
					Integer num = genreOptionsInt(genres);
					
					System.out.println("Choose Genre to Update");
					System.out.println(listOfGenres);
					
					nxtAction = read_range(userSelection, 1, num);
					
					genreId = genres.get(nxtAction -1).getGenreId();
					
					updatedGenre = getUpdatedGenre();
					updatedGenre.setGenreId(genreId);
					updatingGenre(updatedGenre);
					System.out.println("Genre has been updated");
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    	
    	    }
    	    
    	    if (nextAction == 3) {
    	    	
    	    	Integer nxtAction;
    	    	List<Genre> genres = new ArrayList<Genre>();
    	    	Integer genreId;
    	    	Genre genreToDelete = new Genre();
    	    	
    	    	try {
    	    		genres = getAllGenre();
					String listOfGenres = genreOptions(genres);
					Integer num = genreOptionsInt(genres);
					
					System.out.println("Choose Genre to Update");
					System.out.println(listOfGenres);
					
					nxtAction = read_range(userSelection, 1, num);
					genreId = genres.get(nxtAction -1).getGenreId();
					
					genreToDelete.setGenreId(genreId);
					deletingGenre(genreToDelete);
					
					System.out.println("Genre has been deleted");
    	    	} catch(SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();	
    	    	}
    	    }
    	    if (nextAction == 4) {
    	    	
    	    	List<Genre> genres = new ArrayList<Genre>();
    	    	
    	    	try {
    	    		genres = getAllGenre();
					String listOfGenres = genreOptions(genres);
					System.out.println(listOfGenres);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    }	
		}
        
        
        
        
        
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Publisher <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        else if (action == 4) {
        	
        	Integer nextAction;
        	
        	System.out.println("1. Add Publisher");
    	    System.out.println("2. Update Publisher");
    	    System.out.println("3. Delete Publisher");
    	    System.out.println("4. View All Publishers");
    	    System.out.println("5. Quit to Previous Menu");
    	    
    	    nextAction = read_range(userSelection, 1,5);
    	    
    	    if (nextAction == 5) {
    	    	displayMenu();
    	    }
    	    
    	    if (nextAction == 1) {
    	    	try {
					savePublisher(newPublisher());
					System.out.println("Publsiher has been added");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    }
    	    
    	    if (nextAction == 2) {
    	    	
    	    	Integer nxtAction;
    	    	List<Publisher> publishers = new ArrayList<Publisher>();
    	    	Integer pubId;
    	    	Publisher updatedPub;
    	    	
    	    	
    	    	try {
    	    		
    	    		publishers = getAllPublishers();
					String listOfPublishers = publisherOptions(publishers);
					Integer num = publisherOptionsInt(publishers);
					
					System.out.println("Choose Publisher to Update");
					System.out.println(listOfPublishers);
					
					nxtAction = read_range(userSelection, 1, num);
					
					pubId = publishers.get(nxtAction -1).getPublisherId();
					
					updatedPub = getUpdatedPublisher();
					updatedPub.setPublisherId(pubId);
					updatingPublisher(updatedPub);
					System.out.println("Publisher has been updated");
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    }
    	    
    	 if (nextAction == 3) {
    		 
    		Integer nxtAction;
 	    	List<Publisher> publishers = new ArrayList<Publisher>();
 	    	Integer pubId;
 	    	Publisher publisherToDelete = new Publisher();
 	    	
 	    	try {
 	    		publishers = getAllPublishers();
				String listOfPublishers = publisherOptions(publishers);
				Integer num = publisherOptionsInt(publishers);
					
				System.out.println("Choose Publisher to Delete");
				System.out.println(listOfPublishers);
					
					nxtAction = read_range(userSelection, 1, num);
					pubId = publishers.get(nxtAction -1).getPublisherId();
					
					publisherToDelete.setPublisherId(pubId);
					deletingPublisher(publisherToDelete);
					
					System.out.println("Publisher has been deleted");
 	    	} catch(SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();	
 	    	}
    	    	
    	 }
    	 
    	 if (nextAction == 4) {
    		 
    		 List<Publisher> publishers = new ArrayList<Publisher>();
    	    	
    	    	try {
    	    		publishers = getAllPublishers();
					String listOfPublishers = publisherOptions(publishers);
					System.out.println(listOfPublishers);
				} catch (SQLException e) {
					e.printStackTrace();
				}
    		 
    	 }
        	
			
		}
        
        
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> End Publisher <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<   
        
        
        
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Branches <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        
        else if (action == 5) {
			
		}
        else if (action == 6) {
			
		}
        
        
        
        
        
        
        
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Loans <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<        
        else if (action == 7) {
        	
        	Integer nextAction;
        	//BorrowerServices bs = new BorrowerServices();
        	List<Book> books = new ArrayList<Book>();
        	List<Book> validBooks = new ArrayList<Book>();
        	Integer bookId;
        	Loans loan;
	    	LocalDate date = LocalDate.now();
        	
        	System.out.println("1. Loan A Book");
    	    System.out.println("2. Update A Loan");
    	    System.out.println("3. Delete A Loan");
    	    System.out.println("4. View All Loan");
    	    System.out.println("5. Quit to Previous Menu");
    	    
    	    nextAction = read_range(userSelection, 1,5);
    	    
    	    if (nextAction == 5) {
    	    	displayMenu();
    	    }
    	    
    	    if (nextAction == 1) {
    	    	Integer nxtAction;
    	    	Copies copy = new Copies();
    	    	List<Branch> branches = new ArrayList<Branch>();
    	    	Integer branchId;
    	    	List<Copies> copies = new ArrayList<Copies>();
    	    	List<Copies> validCopies = new ArrayList<Copies>();
    	    	
    	    	
    	    	try {
					branches = bs.getAllBranches();
					books = bs.getAllBooks();
					copies = getAllCopies();
					loan = newLoans(); // already handled the cardNo thing in the newLoans() Function
					
					String listOfBranches = bs.branchOptions(branches);
					Integer num = bs.branchOptionsInt(branches);
					System.out.println("Choose A Branch");
					System.out.println(listOfBranches);
					nxtAction = read_range(userSelection, 1, num);
					branchId = branches.get(nxtAction - 1).getBranchId();
					
					for (int i = 0; i < copies.size(); i++) {
						if (copies.get(i).getBranchId() == branchId) {
							validCopies.add(copies.get(i));
						} 
					}
					
					for (int i = 0; i < validCopies.size(); i++) {
						for (int j = 0; j < books.size(); j++) {
							if(validCopies.get(i).getBookId() == books.get(j).getBookId()) {
								validBooks.add(books.get(j));
							}
						}
					}
					
					System.out.println("Choose a Book");
					String listOfBooks = bs.bookOptions(validBooks);
					num = bs.bookOptionsInt(validBooks);
					System.out.println(listOfBooks);
					nxtAction = read_range(userSelection, 1, num);
					bookId = validBooks.get(nxtAction - 1).getBookId();
					
					for (int i = 0; i < validCopies.size(); i++) {
						if (validCopies.get(i).getBookId() == bookId && validCopies.get(i).getCopies() != 0) {
							copy = validCopies.get(i);
						}	
					}
					
					if (copy != null) {
						
						//try to update the copy number here.
						
						loan.setBookId(bookId);
						loan.setBranchId(branchId);
						loan.setDateOut(date);
						loan.setDueDate(date.plusDays(7));
						loan.setDateIn(null);
						
						saveLoans(loan);
						
					} else {
						System.out.print("No Copy of this book is currently available");
					}	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    	
    	    }
    	    
    	    if (nextAction == 2) {
    	    	Integer nxtAction;
    	    	List<Loans> loans = new ArrayList<Loans>();
    	    	List<Loans> validLoans = new ArrayList<Loans>();
    	    	
    	    	
    	    	try {
					loan = newLoans();
					loans = getLoanForUpdate(loan);// Find a way to make this function only return entries with dateIn values of null
					books = bs.getAllBooks();      // Also the LoansDAO isnt assigning null to the dateIn field thus throwing a nullpointer error
					System.out.println(loans.get(0).getDateOut());
					for (int i = 0; i < loans.size(); i++) {
						for (int j = 0; j < books.size(); j++) {
							if (loans.get(i).getBookId() == books.get(j).getBookId()) {
								validBooks.add(books.get(j));
							}
						}
					}
					
					System.out.println("Choose a Book_Loan Transaction to Edit");
					String listOfBooks = bs.bookOptions(validBooks);
					Integer num = bs.bookOptionsInt(validBooks);
					System.out.println(listOfBooks);
					nxtAction = read_range(userSelection, 1, num);
					bookId = validBooks.get(nxtAction - 1).getBookId();
					
					for (int i = 0; i < loans.size(); i++) {
						if (loans.get(i).getBookId() == bookId) {
							loan = loans.get(i);
						}
					}
					 System.out.println("Enter New Due Date: Example 2020-02-23");
					 String dueDate = userSelection.nextLine(); //do some checking here to make sure they enter correct date style
					 											// nextLine() not waiting for me to enter any thing
					 LocalDate updatedDueDate = LocalDate.parse(dueDate);
					 
					 loan.setDueDate(updatedDueDate);
					 updatingLoan(loan);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	finally {
					displayMenu();
				}
    	    	

    	    }
    	    
	    	if (nextAction == 3) {
	    	
	        	Integer nxtAction;
    	    	List<Loans> loans = new ArrayList<Loans>();
    	    	List<Loans> validLoans = new ArrayList<Loans>();
    	    	
    	    	
    	    	try {
					loan = newLoans();
					loans = getLoanForUpdate(loan);// Find a way to make this function only return entries with dateIn values of null
					books = bs.getAllBooks();      // Also the LoansDAO isnt assigning null to the dateIn field thus throwing a nullpointer error
					System.out.println(loans.get(0).getDateOut());
					for (int i = 0; i < loans.size(); i++) {
						for (int j = 0; j < books.size(); j++) {
							if (loans.get(i).getBookId() == books.get(j).getBookId()) {
								validBooks.add(books.get(j));
							}
						}
					}
					
					System.out.println("Choose a Book_Loan Transaction to Delete");
					String listOfBooks = bs.bookOptions(validBooks);
					Integer num = bs.bookOptionsInt(validBooks);
					System.out.println(listOfBooks);
					nxtAction = read_range(userSelection, 1, num);
					bookId = validBooks.get(nxtAction - 1).getBookId();
					
					for (int i = 0; i < loans.size(); i++) {
						if (loans.get(i).getBookId() == bookId) {
							loan = loans.get(i);
						}
					}
					deletingLoan(loan);
					System.out.println("Loan Deleted Successfully");
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	finally {
					displayMenu();
				}
    		
	    	}
	    	

	    	if (nextAction == 4) {
	    		List<Loans> loans = new ArrayList<Loans>();
    	    	
    	    	try {
    	    		loans = getAllLoans();
					String listOfLoans = loanOptions(loans); // Need to do some extra formatting for this 
					System.out.println(listOfLoans);        //view loans to make sense
				} catch (SQLException e) {
					e.printStackTrace();
				}
	    	}
			
    	}
        
        
        
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> End Loans <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        else if (action == 8) {
			
    	}
        
        else if (action == 9) {
        	
        	main.displayMenu();
			
		}
        
	}

//============================================Author Stuff=============================================================
	
	public List<Author> getAllAuthors() throws SQLException {
		Connection conn = null;
		List<Author> b = new ArrayList<Author>();
		try {
			conn = connUtil.getConnection();
			
			AuthorDAO adao = new AuthorDAO(conn);
			b = adao.readAuthors();
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException  e) {
			
			conn.rollback();
			
			System.out.println("Something failed with add Author");
		} finally {
			conn.close();
		}
		
		return b;
	}
	
	public void saveAuthor(Author author) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			adao.addAuthor(author);
			if (author.getBooks() == null) {
				conn.commit();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with add Author");
		} finally {
			conn.close();
		}
	}
	
	public void saveAuthorWithBooks(Author author) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			for(Book b: author.getBooks()) {
				adao.insertBookAuthors(author, b);
			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with add Author");
		} finally {
			conn.close();
		}
	}
	
	public String authorOptions(List<Author> list) {
		int counter = 1;
		String str = null;
		StringBuilder strBuilder = new StringBuilder();
		for (Author b : list) {
			strBuilder = strBuilder.append(counter+". "+b.getAuthorName()+"\n");
			counter++;
		}	
		str = strBuilder.toString();
		return str;
	}
	
	public Integer authorOptionsInt(List<Author> list) {
		int counter = 0;
		for (@SuppressWarnings("unused") Author b : list) {
			counter++;
			continue;
		}
		return counter;
	}
	
	public String getAuthorName() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Author's Name");
		String name = sc.nextLine();
		
		return name;
	}
	
	
	public void updatingAuthor(Author author) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			adao.updateAuthor(author);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with update publisher");
		} finally {
			conn.close();
		}
	}
	
	public void deletingAuthor(Author author) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			adao.deleteAuthor(author);
			adao.deleteBookAuthors(author);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with add Author");
		} finally {
			conn.close();
		}
	}
	

	

//============================================Book Stuff================================================================
//	
//	public List<Book> getAllBooks() throws SQLException {
//		Connection conn = null;
//		List<Book> b = new ArrayList<Book>();
//	
//		try {
//			conn = connUtil.getConnection();
//			
//			BookDAO bdao = new BookDAO(conn);
//			b = bdao.readBooks();
//			
//			conn.commit();
//		} catch (ClassNotFoundException | SQLException  e) {
//			
//			conn.rollback();
//			
//			System.out.println("Something failed with reading Branch");
//		} 
//		finally {
//		conn.close();
//		}
//		return b;
//	}
	
	public void deletingBook(Book book) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			bdao.deleteBook(book);
			bdao.deleteBookAuthors(book);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with add Author");
		} finally {
			conn.close();
		}
	}
	
	public void updateBook(Book book, Publisher pub) throws SQLException {
		Connection conn = null;
		
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			bdao.updateBook(book, pub);
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public String getBookTitle() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Book Title");
		String bookTitle = sc.nextLine();
		
		return bookTitle;
		
	}
	public Genre getBookGenre() {
		Scanner sc = new Scanner(System.in);
		Genre genre = new Genre();
		List<Genre> genres = new ArrayList<Genre>();
		Integer nxtAction;
		//Integer genreId;
		
		try {
			genres = getAllGenre();
			String listOfGenres = genreOptions(genres);
			Integer num = genreOptionsInt(genres);
			
			System.out.println("Choose Genre to Update");
			System.out.println(listOfGenres);
			
			nxtAction = read_range(sc, 1, num);
			
//			genreId = genres.get(nxtAction -1).getGenreId();
			genre = genres.get(nxtAction - 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return genre;
	}
	
	public List<Author> getBooksAuthors() {
		Author author = new Author();
		List<Author> authors = new ArrayList<Author>();
		List<Author> bookAuthors = new ArrayList<Author>();
		Scanner n = new Scanner(System.in);
		Integer opt;
		try {
			authors = getAllAuthors();
			String listOfAuthors = authorOptions(authors);
			Integer num = authorOptionsInt(authors);
			System.out.println("Please Choose The Author(s) to Add.\n"+"Or Enter '0' to add new Author");
			System.out.println(listOfAuthors);
			opt = read_range(n, 1, num);
			
			if (opt == 0) {
				
				String authorName = getAuthorName();
				author.setAuthorName(authorName);
				saveAuthor(author);
				bookAuthors.add(author);
				
			}
			
			if (opt > 0) {
				while (opt != 0) {
					bookAuthors.add(authors.get(opt - 1));
					System.out.println("Enter The Key Of Author(s) You Wish To Add.\n Enter '0' When You're Done Adding Authors\n");
					System.out.println(listOfAuthors); //add check here to make sure youre not adding the same Book objects over and over
					opt = read_range(n, 1, num);	
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookAuthors;
	}
	
	public Book getBookPublisher() {
		Integer opt;
		Integer pubId;
		Integer pk;
		Book book = new Book();
		List<Publisher> publishers = new ArrayList<Publisher>();
		Scanner n = new Scanner(System.in);
		
		try {
			publishers = getAllPublishers();
			String listOfPublishers = publisherOptions(publishers);
			Integer num = publisherOptionsInt(publishers);
			System.out.println("Please Choose Book Publisher.\n"+"Or Enter '0' to add new publisher");
			System.out.println(listOfPublishers);
			opt = read_range(n, 1, num);
			
			if (opt == 0) {
				
				savePublisher(newPublisher());
				publishers = getAllPublishers();
				pubId = publishers.get(publishers.size() - 1).getPublisherId();
				book.setPublisherId(pubId);
				
				
			}
			
			if (opt > 0) {
				pubId = publishers.get(opt - 1).getPublisherId();
				book.setPublisherId(pubId);
				
			}
		} catch (SQLException e) {
			System.out.println("Something went wrong with gettng the publisher");
		}
		
		return book;
	}
	
	public Book getBookAuthors(Book book) {
		Integer opt;
		Integer authorId;
		Integer pk;
//		Book book = new Book();
		List<Author> authors = new ArrayList<Author>();
		List<Author> bookAuthors = new ArrayList<Author>();
		Scanner n = new Scanner(System.in);
		
		
		try {
			authors = getAllAuthors();
			String listOfAuthors = authorOptions(authors);
			Integer num = authorOptionsInt(authors);
			System.out.println("Please Choose Book's Author.\n"+"Or Enter '0' to Add New Author");
			System.out.println(listOfAuthors);
			opt = read_range(n, 1, num);
			
			
			if (opt == 0) {
				System.out.println("How many Authors do you want to add");
				Integer numOfAuthorsToAdd = n.nextInt();
				
				for(int i = 0; i < numOfAuthorsToAdd; i++) {
					Author author = new Author();
					  System.out.println("Enter next Author's Full Name: ");
					  String authorName = n.nextLine();
					  author.setAuthorName(authorName);
					  
					  bookAuthors.add(author);
					}
				
				book.setAuthors(bookAuthors);
			}
			
			if (opt > 0) {
				authorId = authors.get(opt - 1).getAuthorId();
				//book.setAuthors(); //how do i actually set the authors???
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
//======================================================================================================================
	
	
	
	
	
	
	
//============================================= Genre Stuff ============================================================
	public void saveGenre(Genre genre) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			gdao.addGenre(genre);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with add Author");
		} finally {
			conn.close();
		}
	}
	
	public Genre newGenre() {
		Genre g = new Genre();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Genre Name");
		String genreName = sc.nextLine();
		
		g.setGenreName(genreName);
		
		return g;
	}
	
	public List<Genre> getAllGenre() throws SQLException {
		Connection conn = null;
		List<Genre> g = new ArrayList<Genre>();
	
		try {
			conn = connUtil.getConnection();
			
			GenreDAO grdao = new GenreDAO(conn);
			g = grdao.readGenre();
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException  e) {
			
			conn.rollback();
			
			System.out.println("Something failed with reading Branch");
		} 
		finally {
		conn.close();
		}
		return g;
	}
	
	public void updatingGenre(Genre genre) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			gdao.updateGenre(genre);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with add Author");
		} finally {
			conn.close();
		}
	}
	
	public Genre getUpdatedGenre() {
		Genre g = new Genre();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Genre Name");
		String genreName = sc.nextLine();
		
		return g;
	}
	
	public String genreOptions(List<Genre> list) {
		int counter = 1;
		String str = null;
		StringBuilder strBuilder = new StringBuilder();
		for (Genre b : list) {
			strBuilder = strBuilder.append(counter+". "+b.getGenreName()+"\n");
			counter++;
		}	
		str = strBuilder.toString();
		return str;
	}
	
	public Integer genreOptionsInt(List<Genre> list) {
		int counter = 0;
		for (@SuppressWarnings("unused") Genre b : list) {
			counter++;
			continue;
		}
		return counter;
	}	
	
	public void deletingGenre(Genre genre) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			gdao.deleteGenre(genre);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with add Author");
		} finally {
			conn.close();
		}
	}
//================================================================================================================
	
//===============================================Publisher Stuff================================================
	
	public void savePublisher(Publisher pub) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			PublisherDao pdao = new PublisherDao(conn);
			pdao.addPublisher(pub);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with add Author");
		} finally {
			conn.close();
		}
	}
	
	
	public Publisher newPublisher() {
		Publisher p = new Publisher();
		
		String pubName;
		String pubAddy;
		String pubPhone;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Publisher Name");
		pubName = sc.nextLine();
		
		System.out.println("Enter Publisher Address");
		pubAddy = sc.nextLine();
		
		System.out.println("Enter Genre Phone");
		pubPhone = sc.nextLine();
		
		p.setPublisherName(pubName);
		p.setPublisherAddress(pubAddy);
		p.setPublisherPhone(pubPhone);
		
		return p;
	}
	
	public List<Publisher> getAllPublishers() throws SQLException {
		Connection conn = null;
		List<Publisher> p = new ArrayList<Publisher>();
	
		try {
			conn = connUtil.getConnection();
			
			PublisherDao pdao = new PublisherDao(conn);
			p = pdao.readPublisher();
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException  e) {
			
			conn.rollback();
			
			System.out.println("Something failed with reading Publishers");
		} 
		finally {
		conn.close();
		}
		return p;
	}
	
	public String publisherOptions(List<Publisher> list) {
		int counter = 1;
		String str = null;
		StringBuilder strBuilder = new StringBuilder();
		for (Publisher b : list) {
			strBuilder = strBuilder.append(counter+". "+b.getPublisherName()+"\n");
			counter++;
		}	
		str = strBuilder.toString();
		return str;
	}
	
	public Integer publisherOptionsInt(List<Publisher> list) {
		int counter = 0;
		for (@SuppressWarnings("unused") Publisher b : list) {
			counter++;
			continue;
		}
		return counter;
	}
	
	public Publisher getUpdatedPublisher() {
		Publisher p = new Publisher();
		

		String pubName;
		String pubAddy;
		String pubPhone;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Publisher Name");
		pubName = sc.nextLine();
		
		System.out.println("Enter Publisher Address");
		pubAddy = sc.nextLine();
		
		System.out.println("Enter Genre Phone");
		pubPhone = sc.nextLine();
		
		p.setPublisherName(pubName);
		p.setPublisherAddress(pubAddy);
		p.setPublisherPhone(pubPhone);
		
		return p;
	}
	
	public void updatingPublisher(Publisher pub) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			PublisherDao pdao = new PublisherDao(conn);
			pdao.updatePublisher(pub);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with update publisher");
		} finally {
			conn.close();
		}
	}
	
	public void deletingPublisher(Publisher pub) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			PublisherDao pdao = new PublisherDao(conn);
			pdao.deletePublisher(pub);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with delete Publisher");
		} finally {
			conn.close();
		}
	}
	
//================================================================================================================
	
	
	
	
//===============================================Branch Stuff=====================================================

	
//================================================================================================================
	
	
	
	
	
	
	
//===============================================Borrower Stuff======================================================

	
//================================================================================================================
	
//===============================================Loans Stuff========================================================
	
	public void saveLoans(Loans loan) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			LoansDAO ldao = new LoansDAO(conn);
			ldao.addLoan(loan);
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with add Loan");
		} finally {
			conn.close();
		}
	}
	
	public Loans newLoans() throws SQLException{
		
			Loans l = new Loans();
			
			Integer cardNo;
			Integer bookId;
			Integer branchId;
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Enter cardNo");
			cardNo = sc.nextInt();
			
			l.setCardNo(cardNo);
			
			return l;
		}
	
	public List<Loans> getLoanForUpdate(Loans loan) throws SQLException {
		Connection conn = null;
		List<Loans> l = new ArrayList<Loans>();
		try {
			conn = connUtil.getConnection();
			LoansDAO ldao = new LoansDAO(conn);
			l = ldao.readLoanForUpdate(loan);
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with add Loan");
		} finally {
			conn.close();
		}
		return l;
	}
	
	public void updatingLoan(Loans loan) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			LoansDAO ldao = new LoansDAO(conn);
			ldao.updateLoan(loan);
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with add Loan");
		} finally {
			conn.close();
		}
		
	}
	
	public void deletingLoan(Loans loan) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			LoansDAO ldao = new LoansDAO(conn);
			ldao.deleteLoan(loan);
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Something failed with add Loan");
		} finally {
			conn.close();
		}
		
	}
	
	public List<Loans> getAllLoans() throws SQLException {
		Connection conn = null;
		List<Loans> l = new ArrayList<Loans>();
	
		try {
			conn = connUtil.getConnection();
			
			LoansDAO ldao = new LoansDAO(conn);
			l = ldao.readLoans();
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException  e) {
			
			conn.rollback();
			
			System.out.println("Something failed with reading Publishers");
		} 
		finally {
		conn.close();
		}
		return l;
	}
	
	public String loanOptions(List<Loans> list) {
		int counter = 1;
		String str = null;
		StringBuilder strBuilder = new StringBuilder();
		for (Loans b : list) {
			strBuilder = strBuilder.append(counter+". "+b.getDueDate()+"\n");
			counter++;
		}	
		str = strBuilder.toString();
		return str;
	}
	
	
	
	
	
//================================================================================================================
	

	
//===============================================Copies Stuff========================================================
	
	public List<Copies> getAllCopies() throws SQLException {
		Connection conn = null;
		List<Copies> c = new ArrayList<Copies>();
	
		try {
			conn = connUtil.getConnection();
			
			CopiesDAO cdao = new CopiesDAO(conn);
			c = cdao.readCopies();
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException  e) {
			
			conn.rollback();
			
			System.out.println("Something failed with reading Branch");
		} 
		finally {
		conn.close();
		}
		return c;
	}
	
	public List<Copies> getCopiesForAdd(Loans loan) throws SQLException {
		Connection conn = null;
		List<Copies> c = new ArrayList<Copies>();
	
		try {
			conn = connUtil.getConnection();
			
			CopiesDAO cdao = new CopiesDAO(conn);
			c = cdao.readCopiesForAdd(loan);
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException  e) {
			
			conn.rollback();
			
			System.out.println("Something failed with reading Branch");
		} 
		finally {
		conn.close();
		}
		return c;
	}
	
	
//================================================================================================================


}
