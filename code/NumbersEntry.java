import java.io.Serializable;

/*
 * Student name: Fuhan Sun
 * Student ID: 1131339
 * LMS username: fuhans
 */



/**
 * This class is a subclass of "Entry" class and it is also the parent class of "AutoNumbersEntry"
 * class. In addition to inheriting id contents for entry, bill and member, the class also 
 * add an array which is for the entry numbers.
 * @author sunfuhan
 * @since 20 June 2021
 */
public class NumbersEntry extends Entry implements Serializable
{
    protected int[] numbers;//the array for entry numbers
    //it is convenient for filling and saving in the project
    
   
    /**
     * constructor with 4 parameters
     * @param entryId
     * @param billId
     * @param memberId
     * @param numbers, one int array which is for numbers of one entry
     */
    public NumbersEntry(int entryId,String billId,String memberId,int[]numbers)
    {
    	super(entryId,billId,memberId);
    	this.numbers=numbers;
    }
    
    
    /**
     * constructor with 3 parameters
     * @param entryId
     * @param billId
     * @param memberId
     */
    public NumbersEntry(int entryId,String billId,String memberId)
    {
    	super(entryId,billId,memberId);
    }
    
    
    /**
     * constructor with no parameter
     */
    public NumbersEntry()
    {
    	
    }
    
    
    /**
     * getter for entry numbers array.
     * @return the array for entry numbers
     */
    public int[] getNumbers()
    {
    	return this.numbers;
    }
    
}
