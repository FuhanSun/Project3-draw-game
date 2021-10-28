import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Set;

/*
 * Student name: Fuhan Sun
 * Student ID: 1131339
 * LMS username: fuhans
 */

/**
 * This class is the competition of Random pick type and the subclass of Competition class.
 * It has the methods which can input bill id, decide the numbers of entries and choose winners.
 * Different with LuckyNumber type, this one does not have entry numbers.
 * @author sunfuhan
 * @since 18 June 2021
 */
public class RandomPickCompetition extends Competition implements Serializable
{
	private Bill bill;
	private int numberOfEntries;//the numbers of entries
	private String typeYN;
	private final int FIRST_PRIZE = 50000;
    private final int SECOND_PRIZE = 5000;
    private final int THIRD_PRIZE = 1000;
    private final int[] prizes = {FIRST_PRIZE, SECOND_PRIZE, THIRD_PRIZE};
    private final int MAX_WINNING_ENTRIES = 3;
	
    /**
     * constructor with no parameter.
     */
    public RandomPickCompetition()
	{
		
	}
    
    
    

    
    
    /**
     * constructor with three parameters.
     * @param compID, the id of competition.
     * @param compName, the name of competition.
     * @param isTestMode, whether the competition testing mode or not.
     */
    public RandomPickCompetition(int compID,String compName, boolean isTestMode)
	{
		super(compID,compName, isTestMode);
	}
   
    /**
     * The method can choose the bill which is available for the drawing winners.
     * A good bill firstly should have correct format which is  6 digits, and it can be found
     * from the bills.csv with member id and "not used" tip.
     */
    public void billHandle()
    {
    	final int IDLENGTH = 6;//the length of bill id
    	
    	while(true)//the choose process for bill which can join the competition
    	{
    	   System.out.println("Bill ID: ");
    	   String billid = SimpleCompetitions.keyboard.next();
    	   SimpleCompetitions.keyboard.nextLine();
    	   try 
    	   {
    	       boolean tf = true;
    	       char[] c = billid.toCharArray();
    	
    	       for(int i=0; i<billid.length(); i++)//check string only has number
    	       {
    		       if(!Character.isDigit(c[i]))
    		       {
    			       tf=false;
    			       break;
    		       }
    	       }
    	       if(billid.length()!= IDLENGTH||tf==false)
    		       throw new Exception();
    	      
    	       if ((bill=SimpleCompetitions.dataPro.getExistBill(billid))==null)
    	       {
    	    	   System.out.println("This bill does not exist. Please try again.");
    	       }
    	       else if((bill=SimpleCompetitions.dataPro.getUsfulBill(billid))==null)
    	       {
    	    	   System.out.println("This bill has no member id. Please try again.");
    	       }
    	       else if (bill.isUsed()==true)
    	       {
    	    	   System.out.print("This bill has already been used for a competition. ");
    	    	   System.out.println("Please try again.");
    	       }
    	       else
    	    	   break;
    	   }
    	   catch(Exception e)
    	   {
    		    System.out.print("Invalid bill id! It must be a 6-digit number. ");
    		    System.out.println("Please try again.");
    	   }
    	  
        }	
    }

    
    /**
     * The method calculates the numbers of entries according to the bill id. 
     */
    public void totalAmount()
    {
    	final int ONE_ENTRY = 50;//one entry needs $50
  
    	double amount = bill.getAmount(); 
    	numberOfEntries =(int)amount/ONE_ENTRY;
    	
    	if(numberOfEntries<1) //condition:amount is less than $50
    	{
    		System.out.print("This bill is not eligible for an entry. ");
    		System.out.println("The total amount is smaller than $50.0");
    	}
    	else
    	{
    	System.out.print("This bill ($"+amount+") is eligible for "+ numberOfEntries);
    	System.out.println(" entries.");
    	bill.setIsUsed(true);
    	}
    	
    }
    
    
    /**
     * This is a conclusive method which includes "billHandle", "totalAmount" and "fillEntries" 
     * methods. Also it can take the selection above adding more entries.
     */
    public void addEntries() 
    {
    	while(true)
		{
    		billHandle();
    		totalAmount();
    	    fillEntries(numberOfEntries, bill.getBillId(),bill.getMemberId());
    	    while(true)//loop for adding more entries
	        {
	             System.out.println("Add more entries (Y/N)?");
                 typeYN = SimpleCompetitions.keyboard.next();
                 if(typeYN.equalsIgnoreCase("N"))
                 {
    	             break;
                 }
                 else if(typeYN.equalsIgnoreCase("Y"))
                 {
    	             break;
                 }
                 else
    	             System.out.println("Unsupported option. Please try again!");
	        }
	        if(typeYN.equalsIgnoreCase("Y"))
                continue;
            else
                break;
		}
    }
    
    
    /**
     * It can save the entries information into the "entries" arrayList and print entry id.
     * @param numberOfEntries, the number of entries.
     * @param bid, the bill id.
     * @param mid, the member id.
     */
    public void fillEntries(int numberOfEntries,String bid,String mid)
    {
    	System.out.println("The following entries have been automatically generated:");
    	for(int i=1;i<=numberOfEntries;i++)//generate entries
    	{
    		Entry e = new Entry(entryId,bid,mid);
    		entries.add(e);
    		System.out.printf("Entry ID: "+"%-6d",entryId);
    		System.out.println();
    		entryId++;
    	}
    }
 
    /**
     * The method select three winning entries automatically. For each member, he/she can only get
     * one winning with highest prize entry.
     * Print the information for winners.
     */
    public void drawWinners() 
    {
    	System.out.print("Competition ID: "+getId()+", ");
		System.out.print("Competition Name: "+getName()+", ");
		System.out.println("Type: RandomPickCompetition");
		System.out.println("Winning entries:");
        Random randomGenerator = null;
        if (this.getIsTestingMode()) //testing type or normal type
        {
            randomGenerator = new Random(this.getId());
        } else 
        {
            randomGenerator = new Random();
        }
		
        int winningEntryCount = 0;
        while (winningEntryCount < MAX_WINNING_ENTRIES&&winningEntryCount <entries.size()) 
        {
            int winningEntryIndex = randomGenerator.nextInt(entries.size());
	
            Entry winningEntry = entries.get(winningEntryIndex);
		    
            /*
             * Ensure that once an entry has been selected,
             * it will not be selected again.
             */
            if (winningEntry.getPrize() == 0) 
            {
                int currentPrize = prizes[winningEntryCount];
                winningEntry.setPrize(currentPrize);
                winningEntryCount++;
                
                Entry trywin;
                //choose only one highest prize entry for each winner member
                if((trywin=win.get(winningEntry.getMid()))==null)
                {
                	win.put(winningEntry.getMid(), winningEntry);
                }
                else if(trywin.getPrize()<winningEntry.getPrize())
                {
                	win.put(winningEntry.getMid(), winningEntry);
                }
            }
        }
        
        //order the winner entries
        ArrayList<Entry> winOrder = new ArrayList<>(win.values());
		Collections.sort(winOrder, new Comparator<Entry>(){
			public int compare (Entry o1,Entry o2)
			{
				return o1.getEntryId()-o2.getEntryId();
			}
		});
		
		//print the winner entries
		for(int i=0;i<winOrder.size();i++)
	    {
			Entry a =winOrder.get(i);
		    System.out.print("Member ID: "+a.getMid());
		    String memid = a.getMid();
		    String m=SimpleCompetitions.dataPro.getgetMemberName(memid);
		  
		    System.out.printf(", Member Name: "+m);
		    System.out.printf(", Entry ID: "+"%-1d",a.getEntryId());
		    System.out.printf(", Prize: "+"%-5d",a.getPrize());
		    System.out.println();
	    }
        
       
        /*
         * Note that the above piece of code does not ensure that
         * one customer gets at most one winning entry. Add your code
         * to complete the logic.
         */
    }
    
}
