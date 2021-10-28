import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Random;

/*
 * Student name: Fuhan Sun
 * Student ID: 1131339
 * LMS username: fuhans
 */


/**
 * This class is the competition of LuckyNumber type and the subclass of Competition class.
 * It has the methods which can input bill id, decide the numbers of entries, gain the entry 
 * numbers and choose winners.
 * Different with RandomPick type, this one has entry numbers.
 * @author sunfuhan
 * @since 18 June 2021
 */
public class LuckyNumbersCompetition extends Competition implements Serializable
{
 
	private ArrayList<Entry> winList;//the ArrayList to save winner entries' information
	private Bill bill;
	private int numberOfEntries;//the numbers of entries
	private String typeYN;
	private int[] lucky;//the array for lucky numbers
	private int manualEntries;//the numbers of manual entries
	
	
	
	/**
	 * constructor with 3 parameters
	 * @param compID, competition id
	 * @param compName, competition name
	 * @param isTestMode, whether the mode is testing type
	 */
	public LuckyNumbersCompetition(int compID,String compName, boolean isTestMode)
	{
		super(compID,compName, isTestMode);
	}
	
	
	
	
	/**
	 * constructor with no parameter
	 */
	public LuckyNumbersCompetition()
	{
		
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
		final int MIN_ENTRY = 1; //the minimum number of entry is 1
    	
		if(numberOfEntries >= MIN_ENTRY)
	    {
		    fillEntries();
	    }
		while(true)//add more entries or not
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
	 * The method can choose the bill which is available for the drawing winners.
     * A good bill firstly should have correct format which is  6 digits, and it can be found
     * from the bills.csv with member id and "not used" tip.
	 */
	public void billHandle() 
	{
	    final int IDLENGTH = 6;//the length of bill id
	 
	    while(true)//loop for choosing bill which can be used in the competition
	    {
	    		
	    	System.out.println("Bill ID: ");
	    	String billid = SimpleCompetitions.keyboard.next();
	    	SimpleCompetitions.keyboard.nextLine();
	    	    
	    	char[] c = billid.toCharArray();
	    	int a =0;

	    	//make sure string only has number
	    	for(int i=0; i<billid.length(); i++)
	    	{
	    	    if(!Character.isDigit(c[i]))
	    		{
    			     a=a+1;
	    		}
	        }
	    	       
	    	//under the condition which the input is 6 digits
	    	if((a==0)&&billid.length()==IDLENGTH)
	    	{
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
		       {
		    	     break;
		       }
	    	}
	    	else
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
	    	System.out.print(" entries. ");
	    	bill.setIsUsed(true);
	    	}
	    	
	    }
	    
	 
	 /**
	  * This is a conclusive method which includes "numbersForAuto" and "numbersForManual" method.
	  * It can get the numbers of manual entries and print them, also print the remaining auto
	  * entries.
	  */
     public void fillEntries()
	 {
		System.out.println("How many manual entries did the customer fill up?: ");
	    while(true)
		{
		  manualEntries = SimpleCompetitions.keyboard.nextInt();
		  SimpleCompetitions.keyboard.nextLine();
		  
		  //the input numbers of manual entry should be in the range 
		  //from 0 to the numbers of entries
		  if(manualEntries>=0&&manualEntries<=numberOfEntries) 
		  {
			  break;
		  }
		  else
		  {
			  System.out.print("The number must be in the range from 0 to "+numberOfEntries);
			  System.out.println(". Please try again.");
		   }
		}
		
    	if(manualEntries==0)
    	{
    		System.out.println("The following entries have been added:");
    		int autoEntries = numberOfEntries-manualEntries;
    		numbersForAuto(autoEntries,bill.getBillId(),bill.getMemberId());
    	}
    	else 
    	{
    		numbersForManual(manualEntries,bill.getBillId(),bill.getMemberId());
    	    int autoEntries = numberOfEntries-manualEntries;
    	    numbersForAuto(autoEntries,bill.getBillId(),bill.getMemberId());
    	}
	}
	
	
     
     /**
      * The method can fill up the auto entries and print them.
      * @param autoEntries, the numbers of auto entries
      * @param billId
      * @param memberId
      */
	public void numbersForAuto(int autoEntries, String billId,String memberId)
	{
		ArrayList<Entry> auto = new ArrayList<Entry>();
		
		for(int i=1; i<=autoEntries;i++)//generate all auto entries
		{	
			AutoNumbersEntry e;
			
			if(this.getIsTestingMode())//mode selection
			{
				e=new AutoNumbersEntry(entryId,billId,memberId,entryId-1);
			}
			else
			{
				Random df = new Random();
    			int randomNumber = df.nextInt();
    			e=new AutoNumbersEntry(entryId,billId,memberId,randomNumber);
			}
            
			entries.add(e);//save to the "entries" ArrayList
			auto.add(e);//save to the "auto" ArrayList which used for printing
			entryId++;
		}
        
        final int ENTRY_LENGTH = 7;
        for(int n=0;n<auto.size();n++)//print all auto entries
  	    {
  		     NumbersEntry en =  (NumbersEntry) auto.get(n);
  		     int[] nu = en.getNumbers();
  		   
  		     System.out.printf("Entry ID: " + "%-2d",en.getEntryId());
     		 System.out.print("     Numbers:");
     		 for(int q=0; q<ENTRY_LENGTH; q++)
     		 {
     			  System.out.printf("%3d",nu[q]);
     		 }
     		 System.out.println(" [Auto]");
  	    }
	}
	
	
	/**
	 * This method make customer input entry numbers which is 7 different numbers and satisfied 
	 * the demand in this project.
	 * @param manualEntries, the numbers of manual entries
	 * @param billId
	 * @param memberId
	 */
	public void numbersForManual(int manualEntries,String billId,String memberId)
	{
		 ArrayList<Entry> manual = new ArrayList<Entry>();
		 final int ENTRY_LENGTH = 7;
		 String [] numbers;
		 int[] number = new int[ENTRY_LENGTH+1];
		 
		for(int i =1; i<=manualEntries;i++) //this for loop is used to input all manual entries
    	{
			 
    	   while(true)	//loop for choosing entry numbers satisfied the demand
    	   {
    	       System.out.print("Please enter 7 different numbers ");
    	       System.out.println("(from the range 1 to 35) separated by whitespace.");
    	       ArrayList<Integer> entryNumbers = new ArrayList<Integer>(ENTRY_LENGTH);
    	       String oneEntry = SimpleCompetitions.keyboard.nextLine();
    	      
    	       //make sure input string is all number
    	       int a =0;
    	       for (int h=0;h<oneEntry.length();h++)
    	       {
    	    	   if(Character.isLetter(oneEntry.charAt(h)))
    	    	   {
    	              a=a+1;
    	    	   }
    	       }
    	       
    	       if(a!=0)//the input string has not only number
    	       {
    	    	   System.out.print("Invalid input! ");
    		    	 System.out.println("Numbers are expected. Please try again!");
    	       }
    	       else//only number
    	       {
    	    	   numbers = oneEntry.split(" ");
    	    	   for(int j=0;j<numbers.length;j++)
	    		   {
	    			   int num = Integer.valueOf(numbers[j]);
	    			   entryNumbers.add(num);
	    		   }
	    		   Collections.sort(entryNumbers);//sort the 7 numbers
	    		   
	    		   if(entryNumbers.size() < ENTRY_LENGTH) //make sure 7 numbers
	    	       {
	    		     System.out.print("Invalid input! ");
	    		     System.out.println("Fewer than 7 numbers are provided. Please try again!");
	               }
	    		   else if(entryNumbers.size() > ENTRY_LENGTH)
	               {
	        	       System.out.print("Invalid input! More than 7 numbers are provided. ");
	        	       System.out.println("Please try again!");
	               }
	    		   else if(entryNumbers.size()== ENTRY_LENGTH)
			       {
				      int sameNumber =0;
	    	          for(int k=0;k<(ENTRY_LENGTH-1);k++) //make sure 7 different numbers
	    	          {
	    		         if(entryNumbers.get(k)==entryNumbers.get(k+1))
	    		         { 
	    		        	 sameNumber += 1;
	    		         }
	    	          }
	    	          if(sameNumber !=0)
	    	          {
	    		         System.out.println("Invalid input! All numbers must be different!");
	    	          }
	    	          else //correct numbers into ArrayList
	    	          {
	    	        	 int b=0;
	    	        	 for(int m=0;m<numbers.length;m++)
	    	        	 {
	    	        		 number[m]=entryNumbers.get(m);
	    	        		 if(number[m]<1||number[m]>35)
	    	        		 {
	    	        			 b=b+1;
	    	        		 }
	    	        	 }
	    	        	 number[numbers.length]=0;
	    	        	 if(b!=0)
	    	        	 {
	    	        		 System.out.print("Invalid input! All numbers must be ");
	    	        		 System.out.println("in the range from 1 to 35!");
	    	        	 }
	    	        	 else
	    	        	 {
	    	        	     NumbersEntry e =new NumbersEntry(entryId,billId,memberId,number);
	    	        	     entries.add(e);//save to "entries" ArrayList
	    	        	     manual.add(e);//save to "manual" AttayList for printing
	    	        	     entryId++;
	    	                 break;
	    	        	 }
	                  }  
			       }
    	       }   
    	   }
    	}
		
		//print all manual entries
		System.out.println("The following entries have been added:");
		
   	    for(int n=0;n<manual.size();n++)
	 	{
	 		 NumbersEntry en =  (NumbersEntry) manual.get(n);
	 		 int[] nu = en.getNumbers();
	 		   
	 		 System.out.printf("Entry ID: " + "%-2d",en.getEntryId());
	         System.out.print("     Numbers:");
	    	 for(int q=0; q<ENTRY_LENGTH; q++)
	    	 {
	    		 System.out.printf("%3d",nu[q]);
	    	 }
	    	 System.out.println();
	 	 }
	}
    	       
    
	
	/**
	 * This is a conclusive method which includes "makeLuckyNumber" and "comparePart" method.
	 */
	public void drawWinners()
	{	
		makeLuckyNumber();
		comparePart();
	}
	
	
	
	/**
	 * It can generate one lucky entry according to the mode type and print the lucky entry.
	 */
	public void makeLuckyNumber()
	{
		AutoNumbersEntry au = new AutoNumbersEntry(); //get lucky entry
      	
		System.out.print("Competition ID: "+getId()+", ");
		System.out.print("Competition Name: "+getName()+", ");
		System.out.println("Type: LuckyNumbersCompetition");
		
      	if(this.getIsTestingMode())//mode selection
		{
      		lucky = au.createNumbers(this.getId());
		}
		else
		{
			Random df = new Random();
			int randomNumber = df.nextInt();
			lucky = au.createNumbers(randomNumber);
		}
      	
      	System.out.print("Lucky Numbers:"); //print the lucky entry      
        for (int j=0; j<lucky.length;j++)
        {
       	    System.out.printf("%3d",lucky[j]);	
        }
        System.out.println(" [Auto]");
	}
	
	
	
	/**
	 * The method make a comparison between members' entries and lucky entry. It picks the entries
	 * with at least 2 same numbers as prize entries. And each member can only have one prize entry 
	 * with most same numbers.Finally print all winner by order.
	 */
	public void comparePart()
	{

		//loop for all entries which have been saved in the "entries" ArrayList
		for(Entry e:entries)
		{
			int pri=0;
			NumbersEntry en = (NumbersEntry) e;
			int[] ent = en.getNumbers();
			
			//see how many numbers in common between lucky entry and member entry
			for(int j =0;j<lucky.length;j++)
			{
				for(int k=0;k<lucky.length;k++)
				{
				    if(ent[k]==lucky[j])
				    {
					    pri+=1;
				    }
				}
			}
			
			int priz= prizecs(pri);//get prize
			e.setPrize(priz);//save the prize into "entries" ArrayList

			//save the winner entry into "win" HashMap 
			//while each winner would only has one winning entry
			Entry trywin;
			if(priz>0)
			{
			    if((trywin=win.get(e.getMid())) == null)
			    {
				    win.put(e.getMid(), e);
			    }
			    else if(trywin.getPrize()<e.getPrize())
			    {
				    win.put(e.getMid(), e);	
			    }
			}
		}
		
		//order the "win" HashMap using "winOrder" ArrayList
		ArrayList<Entry> winOrder = new ArrayList<>(win.values());
		Collections.sort(winOrder, new Comparator<Entry>(){
			public int compare (Entry o1,Entry o2)
			{
				return o1.getEntryId()-o2.getEntryId();
			}
		});
				
		//print all winning entries
		System.out.println("Winning entries:");
		
		for(int i=0;i<winOrder.size();i++)
	    {
			Entry a =winOrder.get(i);
			NumbersEntry b =  (NumbersEntry) a;
			System.out.print("Member ID: "+a.getMid());
			String memid = a.getMid();
			String m = SimpleCompetitions.dataPro.getgetMemberName(memid);
			  
			System.out.printf(", Member Name: "+m);
			System.out.printf(", Prize: "+"%-5d",a.getPrize());
			System.out.println();
			System.out.printf("--> Entry ID: "+"%-1d",a.getEntryId());
			System.out.print(", Numbers:");
			   
	        for(int j=0;j<lucky.length;j++)
	        {
	           	 System.out.printf("%3d",b.getNumbers()[j]);
	        }
	        if(b.getNumbers().length==8)
	        {
	      	     System.out.println();
	        	   
	        }
	        else
	        {
	        	 System.out.println(" [Auto]");
	        }
		}
	}
	
	/**
	 * It has the detailed prizes according to the numbers in common.
	 * @param p, the numbers in common between lucky entry and member entry
	 * @return the prize corresponding with the numbers in common
	 */
	public int prizecs(int p)
	{
		 String priz = String.valueOf(p);
		 int prize=0;
	     if(priz.equalsIgnoreCase("2"))
	     {
	    		prize=50;
	     }
	     else if (priz.equalsIgnoreCase("3"))
	     {
	    		prize=100;
	     }
	     else if (priz.equalsIgnoreCase("4"))
	     {
	    		prize=500;
	     }
	     else if (priz.equalsIgnoreCase("5"))
	     {
	    		prize=1000;
	     }
	     else if (priz.equalsIgnoreCase("6"))
	     {
	    		prize=5000;
	     }
	     else if (priz.equalsIgnoreCase("7"))
	     {
	    		prize=50000;
	     }
	     else
	     {
	    		prize=0;
	     }
	     return prize;
	}
	
}
