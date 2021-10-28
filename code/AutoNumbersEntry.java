/*
 * Student name: Fuhan Sun
 * Student ID: 1131339
 * LMS username: fuhans
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


/**
 * It is a subclass of "NumbersEntry" class used for generating auto entry numbers 
 * in the LuckyNumber competition.
 * @author sunfuhan
 * @since 17 June 2021
 */
public class AutoNumbersEntry extends NumbersEntry implements Serializable
{
    private final int NUMBER_COUNT = 7;//entries would has 7 numbers
    private final int MAX_NUMBER = 35;//the maximum number used for entry 
	
    
    
    /**
     * constructor with no parameter
     */
    public AutoNumbersEntry()
    {
    	
    }
    
    
    
    /**
     * constructor with 4 parameters
     * @param entryId
     * @param billId
     * @param memberId
     * @param seed, which is used for generating different auto entry numbers
     */
    public AutoNumbersEntry(int entryId,String billId, String memberId, int seed)
    {
    	super(entryId,billId,memberId);
    	this.numbers=createNumbers(seed);
    }
    
    /**
     * The method for generating one auto entry numbers.
     * @param seed, the parameter for generating different auto entry numbers
     * @return one array for entry numbers
     */
    public int[] createNumbers (int seed) 
    {
        ArrayList<Integer> validList = new ArrayList<Integer>();
	    int[] tempNumbers = new int[NUMBER_COUNT];
        for (int i = 1; i <= MAX_NUMBER; i++) 
        {
    	    validList.add(i);
        }
        Collections.shuffle(validList, new Random(seed));
        for (int i = 0; i < NUMBER_COUNT; i++) 
        {
    	    tempNumbers[i] = validList.get(i);
        }
        Arrays.sort(tempNumbers);
        return tempNumbers;
    }



}
