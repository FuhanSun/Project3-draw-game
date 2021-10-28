/*
 * Student name: Fuhan Sun
 * Student ID: 1131339
 * LMS username: fuhans
 */

import java.util.Scanner;
import java.util.Set;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;



/**
 * This class is the parent class for "RandomPickCompetition" and "LuckyNumberCompetition" class.
 * It defines the variables, ArrayList and HashMap which would be used in the subclasses.
 * @author sunfuhan
 * @since 17 June 2021
 */
public abstract class Competition implements Serializable
{
    private String name; //competition name
    private int id; //competition identifier
    private boolean isTestMode;//if mode is testing type, the parameter is true
    
    //entry id is used in more than three classes, more convenient using protected
    protected int entryId = 1;

    //ArrayList for all entries information which would be used in all classes
    public ArrayList<Entry> entries = new ArrayList<Entry>();
    //ArrayList for winning entries, more efficient for the extensive usage
    public HashMap<String,Entry> win = new HashMap<String,Entry>();
    //good for using in other classes
    public boolean savedActiveComp = false;
    
   
    /**
     * constructor with 3 parameters
     * @param id, the competition id
     * @param name, the competition name
     * @param isTestMode, the selection of mode. if selection is test mode, parameter is true.
     */
    public Competition(int id, String name, boolean isTestMode)
    {
    	this.id=id;
    	this.name=name;
    	this.isTestMode=isTestMode;
    	
    }
   
    
    
    /**
     * constructor with no parameter
     */
    public Competition()
    {
    	
    }

    
    /**
     * abstract method which would show the method body in the two subclasses for adding entries.
     */
    public abstract void addEntries();

    
   /**
    * abstract method which would show the method body in the two subclasses for drawing winners.
    */
    public abstract void drawWinners();
    
    
    /**
     * Method for calculating the total prizes in one competition which would be printed 
     * in the summary report.
     * @return the total prizes in one competition
     */
    public int totalPrize()
    {
    	int totalPriz=0;
    	
    	//gain all winning entries' prizes
    	Set<String >key=win.keySet();
    	for (String string:key)
    	{	
		  Entry a = win.get(string);
    	
		  int total=a.getPrize();
		  totalPriz=totalPriz+total;
    	}
    	return totalPriz;
    }
  
    
    
    public String getName()
    {
    	return this.name;
    } 
    
    
    
    
    public int getId()
    {
    	return this.id;
    } 
    
    
    
    
    public boolean getIsTestingMode()
    {
    	return this.isTestMode;
    } 
    
   

}
