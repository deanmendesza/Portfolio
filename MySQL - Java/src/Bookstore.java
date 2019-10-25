import java.sql.*;
import java.util.Scanner;

public class Bookstore {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	        
        try (
    
            // connection
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ebookstore", "root", "Dm136425!");
            Statement stmt = conn.createStatement(); 
            
        ) {
        	
            // output to user
            System.out.println(
                    "Please press 1 to continue or 0 to quit!");
            Scanner in = new Scanner(System.in);
            int recordsAffected;
        		         
        	// output to user
            System.out.println(
                    "Hello there! Welcome to Joburgs Bookstore! \n"
                    + "Enter the number of the task you would like to do today: \n"
                    + "1. Enter new book/books into database \n"
                    + "2. Update book information \n"
                    + "3. Delete books from database \n"                        
                    + "4. Search database to find specific book \n"
                    + "0. Quit");
            int choice = in.nextInt();
            	            
            // Options to perform on database
            if (choice == 0) {
                System.out.println("GoodBye");

            } else if (choice == 1) {   // insert into database

                String sqlInsert = insert();
                System.out.println("The SQL query is: " + sqlInsert);
                recordsAffected = stmt.executeUpdate(sqlInsert);
                System.out.println(recordsAffected + " records affected");

            } else if (choice == 2) {   // update database

                String sqlUpdate = update();
                System.out.println("The SQL query is: " + sqlUpdate);
                recordsAffected = stmt.executeUpdate(sqlUpdate);
                System.out.println(recordsAffected + " records affected");

            } else if (choice== 3) {    // delete from database

                String sqlDelete = delete();
                System.out.println("The SQL query is: " + sqlDelete);
                recordsAffected = stmt.executeUpdate(sqlDelete);
                System.out.println(recordsAffected + " records affected.\n");
            
            } else if (choice == 4) {   // search database

                String sqlSearch = search();
                System.out.println("The SQL query is: " + sqlSearch);
                ResultSet rset = stmt.executeQuery(sqlSearch);
                                        
                while (rset.next()) { 
                    System.out.println(rset.getInt("ID") + ", " +
                    rset.getString("Title") + ", " + rset.getString("Author") + ", " +
                    rset.getInt("Qty") + ";"); 
                }

            } else {
                System.out.print("Invalid Entry");
            }
            
        	// close scanner object
        	in.close();
        
		} catch (SQLException e) {
            e.printStackTrace();
        }
	}


    // inserting into database
    public static String insert() {
   
        Scanner insert = new Scanner(System.in);
        // enter book title
        System.out.println("Please enter book title: \t");
        String title = insert.nextLine();
        // enter book author
        System.out.println("Please enter book author: \t");
        String author = insert.nextLine();
        // enter book quantity
        System.out.println("Please enter book quantity: \t");
        int qty = insert.nextInt();
        // enter book id
        System.out.println("Please enter book ID: \t");
        int id = insert.nextInt();

        // SQL query to insert a record
        String sqlInsert = "insert into books " + "values (" + id
            + ", '" + title + "', '" + author + "', " + qty + ");";
        // Display new book information back to user
        System.out.println("New book information:\n"
            + "ID:\t" + id
            + "\nTitle:\t" + title
            + "\nAuthor:\t" + author
            + "\nQuantity:\t" + qty);
        
        // close scanner object
        insert.close();
        return sqlInsert;

    }

    // update books in databases
    public static String update() {

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the Book ID you wish to edit:");
        int editBook = in.nextInt();
       
        // ask user for the specific operation they would like to perform
        System.out.println("What would you like to update? \n"
            + "1. Book ID\n"
            + "2. Book Title\n"
            + "3. Book Author\n"
            + "4. Book Quantity\n"
            + "0. Quit");
        int choice = in.nextInt();
        
        String sqlUpdate = "";
        Scanner input = new Scanner(System.in);
        
        if (choice == 0) {
            // quit option
            System.out.println("Quit");
        }
        else if (choice == 1) {
            // book ID
            System.out.println("Please enter the new book ID: \n");
            int id = input.nextInt();
            sqlUpdate += "update books set ID = " + id + " where ID = " + editBook + ";";
            System.out.println("The new ID is:\t " + id);
        }
        else if (choice == 2) {
            // book Title      	
            System.out.println("Please edit the title: \n");
            String title = input.nextLine();
            // update statement
            sqlUpdate += "update books set Title = \""  + title + "\" where ID = " + editBook + ";";
            System.out.println("The new Title is:\t" + title);
        }
        else if (choice == 3) {
            // book Author
            System.out.println("Please edit the author: \t");
            String author = input.nextLine();
            sqlUpdate += "update books set Author = \"" + author + "\" where ID = " + editBook + ";";
            System.out.println("The new Author is\t:" + author);
    	}
        else if (choice == 4) {
            // Book quantity
            System.out.println("Please edit the quantity: \t");
            int qty = input.nextInt();
            sqlUpdate += "update books set Qty = " + qty + " where ID = " + editBook + ";";
            System.out.println("The new Quantity is\t: " + qty);
        }
        else {
        	System.out.println("Invalid Option");
        }
        // close scanner object
        in.close();
        input.close();
        return sqlUpdate;
    }

    // deleting from database
    public static String delete() {

        Scanner in = new Scanner(System.in);
        System.out.println("\nWould you like to delete: \n"
            + "1. A range of books \n"
            + "2. A specific book");
        int choice = in.nextInt();
        String sqlDelete = "";

        switch (choice) {
            // delete range of books
            case 1:
                System.out.println("Please enter starting range: \n");
                int rangeStart = in.nextInt();  // starting range
                System.out.println("Please enter ending range: \n");
                int rangeEnd = in.nextInt();    // ending range
                sqlDelete += "delete from books where ID between " + rangeStart + " and " + rangeEnd + ";";
                System.out.println("Book deleted from " + rangeStart
                    + " to " + rangeEnd);
                break;

            // delete a specific book
            case 2:
                System.out.println("Please enter the ID of the book you wish to delete: \n");
                int deleteId = in.nextInt();    // book id to delete
                sqlDelete += "delete from books where ID = " + deleteId + ";";
                System.out.println("Deleted book with the ID:\t " + deleteId);
                break;
        }
        // close scanner object
        in.close();
        return sqlDelete;
    }

    // search the database
    public static String search() {

        Scanner in = new Scanner(System.in);
        System.out.println("Would you like:\n"
            + "1. To see enitre database?\n"
            + "2. To search for a specific book?");
        int choice = in.nextInt();
        String strSearch = "";

        if (choice == 1) {
            // see entire database
            strSearch += "select * from books;";
            System.out.println("See entire Database option selected");
        }
        else if (choice == 2) {
            // search for a specific book
            System.out.println("Please enter the Books ID:");
            int id = in.nextInt();  // book id
            strSearch += "select * from books where id = " + id + ";";
            System.out.println("Search for book with an ID of:\t " + id); 
        }
        else {
        	System.out.println("Incorrect number entered!");
        }
        // close scanner object
	        in.close();
	        return strSearch;
	    }
}
