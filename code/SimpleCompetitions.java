/*
 * Student name: Fuhan Sun
 * Student ID: 1131339
 * LMS username: fuhans
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is the main application includes the main method.
 * It provides the welcome message,the content whether load binary file or not,the selection 
 * of mode,the input of files for members and bills and the selection of different options.
 * 
 * @author sunfuhan
 * @since 18 June 2021
 */

public class SimpleCompetitions  implements Serializable
{
	private int compID;//the competition id
	private boolean isTestMode;//if mode is testing type, the parameter would be true
	private boolean active;//if it has an active competition now
	private Competition activeComp;//the competition which is running now
	private ArrayList<Competition> competitions = new ArrayList<Competition>();
	//the ArrayList for all competitions' contents
	private String memberFile;//the input file for members
	private String billFile;//the input file for bills
	private String binaryFileName;//the binary file which would save all competitions' information
	public static DataProvider dataPro;
	//need to use member file and bill file for all classes which are in the DataProvider class
	//using public static can reduce more codes and  raise efficiency
	public static Scanner keyboard;//need to use scanner in all classes
	
	
    
    /**
    * Main program that uses the main SimpleCompetitions class, it calls all methods for 
    * this project.
    * @param args main program arguments
     * @throws DataFormatException, error control for data which has wrong format.
     * @throws DataAccessException, error control for data which can not be found.
     * @throws IOException 
     * @throws FileNotFoundException 
     * @throws ClassNotFoundException 
    */
    public static void main(String[] args) throws DataAccessException, DataFormatException,
                                       FileNotFoundException, IOException, ClassNotFoundException 
    {
    	System.out.println("----WELCOME TO SIMPLE COMPETITIONS APP----");
    	//Create an object of the SimpleCompetitions class
        SimpleCompetitions sc = new SimpleCompetitions();
        keyboard = new Scanner(System.in);
        
        sc.loadModeFile();
        sc.selectOption();
    }
    
    
    /**
     * The method is used for the option of load binary file which includes all competitions 
     * information, the selection of mode and call the "inputMBFile" method.
     * @throws DataAccessException, error control for data which can not be found.
     * @throws DataFormatException, error control for data which has wrong format.
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void loadModeFile() throws DataAccessException, DataFormatException, 
                               FileNotFoundException, ClassNotFoundException, IOException
    {
    	while(true)//the loop is for selection of loading binary file or not
    	{
    		System.out.println("Load competitions from file? (Y/N)?");
    		String yOrN = keyboard.next();
    		if(yOrN.equalsIgnoreCase("Y"))
    		{
    			System.out.println("File name:");
    			binaryFileName = keyboard.next();
    			loadBinaryFile(binaryFileName);
    			isTestMode=competitions.get(competitions.size()-1).getIsTestingMode();
    			break;
    		}
    		else if(yOrN.equalsIgnoreCase("N"))//only selecting "no" needs to choose mode
    		{
    			while(true)
    	    	{
    	    		 System.out.print("Which mode would you like to run? ");
    	             System.out.println("(Type T for Testing, and N for Normal mode):");
    	             String mode = keyboard.next();
    	            
    	             if(mode.equalsIgnoreCase("T"))
    	             {
    	             	isTestMode = true;
    	             	break;
    	             }
    	             else if(mode.equalsIgnoreCase("N"))
    	             {
    	            	isTestMode = false;
    	              	break;
    	             }
    	             else
    	             	System.out.println("Invalid mode! Please choose again.");
    	    	}
    			     break;
    		}
    		else
    			System.out.println("Unsupported option. Please try again!");
    		
    	}
    	
    	inputMBFiles();
    }
    
    
    
    /**
     * Using for the input of member file and bill file.
     * @throws DataAccessException, error control for data which can not be found.
     * @throws DataFormatException, error control for data which has wrong format.
     */
    public void inputMBFiles() throws DataAccessException, DataFormatException
    {
    	try//input member file and bill file with error control
    	{
    		System.out.println("Member file: ");
    	    memberFile = keyboard.next();

    	    System.out.println("Bill file: ");
    	    billFile = keyboard.next();
    	    //put the two files into DataProvider class to read them
    	    dataPro = new DataProvider(memberFile, billFile);
    	    
    	}
    	catch(DataAccessException e)
    	{
    		throw new DataAccessException(memberFile,billFile);
    	}		
    	catch(DataFormatException e)
    	{
    		throw new DataFormatException();
    	}
    }
    
    
    
    /**
     * This method help customers to select different process for the competition.
     * customers can use this method to create a new competition, add entries, get winners, take 
     * summary report and exit the project.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void selectOption() throws FileNotFoundException, IOException
    {
    	while(true)//loop for selecting different processes
    	{
    	System.out.println("Please select an option. Type 5 to exit.");
    	System.out.println("1. Create a new competition");
    	System.out.println("2. Add new entries");
    	System.out.println("3. Draw winners");
    	System.out.println("4. Get a summary report");
    	System.out.println("5. Exit");
    	String option;
    	
    	if (keyboard.hasNextInt())
    	{
    		option=keyboard.next();
    		
    		if(option.equalsIgnoreCase("1"))
    		{
    		  if(active==false)
    		  {
    			addNewCompetition();
    		  }
    		  else
    		  {
    			System.out.print("There is an active competition. ");   
    	    	System.out.println("SimpleCompetitions does not support concurrent competitions!");   
    	    	
    		  }
    		}
    		else if(option.equalsIgnoreCase("2"))
    		{
    			if(active == false)
		    	 {
		    		 System.out.println("There is no active competition. Please create one!");
		    	 }
		    	 else
		    	 {	
		    		 this.activeComp.addEntries();
		    	 }
		    	
    		}
    		else if(option.equalsIgnoreCase("3"))
    		{
    			if(active == false)
		    	 {
		    		 System.out.println("There is no active competition. Please create one!");
		    	 }
		    	 else
		    	 {
		    		 if(this.activeComp.entries.size()==0)
		    		 {
		    			 System.out.println("The current competition has no entries yet!");
		    		 }
		    		 else
		    		 {
		    		 this.activeComp.drawWinners();
		    		 active=false;//means the active competition end
		    		 activeComp=null;
		    		 }
		    	 }
    		}
    		else if(option.equalsIgnoreCase("4"))
    		{
    			if(competitions.size()==0)
    			{
    				 System.out.println("No competition has been created yet!");
    			}
    			else
    			report();
    		}
    		else if(option.equalsIgnoreCase("5"))
    		{
    			while(true)
    	    	{
    	    	System.out.println("Save competitions to file? (Y/N)?");
    	    	String yOrN = keyboard.next();
    	    	if(yOrN.equalsIgnoreCase("N"))
    	    	{
    	    		System.out.println("Goodbye!");
    	    		System.exit(0);
    	    	}
    	    	else if(yOrN.equalsIgnoreCase("Y"))
    	    	{
    	    		saveBinaryFile();//save all competitions into binary file
    	    		dataPro.renewBillFile();//change the isUsed parameter in the bill file
    	    		System.out.println("Competitions have been saved to file.");
    	    		System.out.println("The bill file has also been automatically updated.");
    	    		System.out.println("Goodbye!");
    	    		System.exit(0);
    	    	}
    	    	else
    	    		System.out.println("Unsupported option. Please try again!");
    	    	}
    		}
    		else
    		{
    			System.out.println("Unsupported option. Please try again!");
    		}
    	}
    	else
    	{
    		option=keyboard.next();
    		System.out.println("A number is excepted. Please try again.");
    	}
    	}
    }
    
    
    
    /**
     * This method is used for adding one new competition, it would select LuckyNumbers or 
     * Random pick competition type. Once selection, customers need provide a competition name. 
     * The method would give a help for taking notes above competition id, name and print a 
     * competition message.
     * @return competition which is running now.
     */
    public Competition addNewCompetition() 
    {
		while(true)//loop for selection of competition type
		{
		   System.out.println("Type of competition (L: LuckyNumbers, R: RandomPick)?:");
		   String type = keyboard.next();
		  
		   if(type.equalsIgnoreCase("L"))
		   {
			compID=competitions.size();
			compID++;
			active=true;
			System.out.println("Competition name: ");
			keyboard.nextLine();
			String compName = keyboard.nextLine();
			
			//put the useful parameters into "L" type competition
			activeComp = new LuckyNumbersCompetition(compID,compName,isTestMode);
			//save the new competition into "competitions" ArrayList
			competitions.add(activeComp);
			System.out.println("A new competition has been created!");
			System.out.print("Competition ID: "+compID+", Competition Name: "+compName);
			System.out.println(", Type: LuckyNumbersCompetition");
			break;
		   }
		   else if(type.equalsIgnoreCase("R"))
		   {
			compID=competitions.size();
			compID++;
			active=true;
			System.out.println("Competition name: ");
			keyboard.nextLine();
			String compName = keyboard.nextLine();
			
			//put the useful parameters into "R" type competition
			activeComp = new RandomPickCompetition(compID,compName,isTestMode);
			//save the new competition into "competitions" ArrayList
			competitions.add(activeComp);
			System.out.println("A new competition has been created!");
			System.out.print("Competition ID: "+compID+", Competition Name: "+compName);
			System.out.println(", Type: RandomPickCompetition");
			break;
		   }
		   else
		   {
			System.out.println("Invalid competition type! Please choose again.");
			
		   }
		}
		return activeComp;
    }
    
    /**
     * The method would be used when customer choose option "5" to exit the project and want to 
     * save all competitions' information which happened just now.
     * @throws FileNotFoundException
     * @throws IOException
     * 
     */
    public void saveBinaryFile() throws FileNotFoundException ,IOException
    {
    	System.out.println("File name:");
        String binaryFile = keyboard.next();
        
    	if(activeComp!=null)//there is an active competition 
    	{
    		activeComp.savedActiveComp=true;
    	}
    	FileOutputStream fos = new FileOutputStream(binaryFile);
    	ObjectOutputStream oos = new ObjectOutputStream(fos);
        for(Competition c:competitions)//save all competitions
        {
        	oos.writeObject(c);
        }
        oos.writeObject(null);
        oos.close();
    }
    
    
    
    /**
     * The method can be used when customers want to download previous competition information
     * to the project. They should provide a correct binary file.
     * @param binaryFileName, String type, the binary file customer provided.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DataAccessException, error control for data which can not be found.
     */
    public void loadBinaryFile(String binaryFileName) throws FileNotFoundException, IOException, 
                                    ClassNotFoundException,DataAccessException
    {
    	try//error control
    	{
    	ObjectInputStream oStream = new ObjectInputStream(new FileInputStream(binaryFileName));
    	Competition temp;
    	while((temp=(Competition)oStream.readObject())!=null)//read all competitions
    	{
    		competitions.add(temp);
    		if(temp.savedActiveComp ==true)//there is an active competition in the file
    		{
    			active=true;
    			activeComp=temp;
    		}
    	}
    	}
    	catch(FileNotFoundException e )
        {
   		 throw new DataAccessException();
    	}
    }
    
    
    /**
     * The method would be used when customer choose option "4" to get a summary report.
     * It reports the numbers of completed and active competitions and each competition 
     * information like id, name, numbers of entries and so on.
     */
    public void report() 
    {
    	System.out.println("----SUMMARY REPORT----");
    	int actCom;//the number of active competition
    	
    	if(active==false)//the numbers of active competition
    	{
    		actCom=0;
    	}
    	else
    	{
    		actCom=1;
    	}
    	int noactComp = competitions.size()-actCom;
    	System.out.println("+Number of completed competitions: "+noactComp);
    	System.out.println("+Number of active competitions: "+actCom);
    	
    	for (Competition last: competitions)//loop for print all competitions
    	{
    		System.out.println();
			System.out.print("Competition ID: "+last.getId()+", ");
			System.out.print("name: "+last.getName()+", ");
			
    		if(last!=activeComp)//if there is no active competition
    		{
    			System.out.println("active: no");
    			System.out.println("Number of entries: "+last.entries.size());
    			System.out.println("Number of winning entries: "+last.win.size());
    			System.out.println("Total awarded prizes: "+last.totalPrize());
    		}
    		else
    		{
    			System.out.println("active: yes");
    			System.out.println("Number of entries: "+last.entries.size());
    		}
    	}
    }
    
}
