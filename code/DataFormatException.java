/*
* Student name: Fuhan Sun
 * Student ID: 1131339
 * LMS username: fuhans
 */


/**
 * This class is for the error control for incorrectly entered data type.
 * @author sunfuhan
 * @since 20 June 2021
 */
public class DataFormatException extends Exception 
{
	
	
	/**
	 * constructor
	 * usage for the wrong input file.
	 */
	public DataFormatException()
	{
		super("wrong file.");
	}
}
