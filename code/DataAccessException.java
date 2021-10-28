/*
 * Student name: Fuhan Sun
 * Student ID: 1131339
 * LMS username: fuhans
 */


/**
 * This class is for the error control for entered data which can not access.
 * @author sunfuhan
 * @since 18 June 2021
 */
public class DataAccessException extends Exception 
{
	
	/**
	 * constructor with no parameter
	 */
	 public  DataAccessException()
	 {
	    	super("Files are not accessible! Please try again!");
	 }
	 
	 
	 
	 /**
	  * constructor with 2 parameters
	  * @param file, the input member file
	  * @param files, the input bill file
	  */
	 public  DataAccessException(String file,String files)
     {
    	super("Files are not accessible! Please try again!");
     }
	 
	 
	 /**
	  * constructor with 1 parameters
	  * @param file, the input binary file foe saving all competitions' contents
	  */
	 public  DataAccessException(String file)
     {
    	super("File is not accessible! Please try again!");
     }
}
