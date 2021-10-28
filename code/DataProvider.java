import java.util.ArrayList;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/*
 * Student name: Fuhan Sun
 * Student ID: 1131339
 * LMS username: fuhans
 */



/**
 * This class is for the loading of member file and bill file which would save to ArrayList for
 * the usage in the project. 
 * It also has the method for renewing the bill file whether the bill  used or not. 
 * In addition, there are methods for bill id, like the existence and validity.
 * Finally, it provides the method for printing the member name during the winners' printing.
 * @author sunfuhan
 * @since 19 June 2021
 */
public class DataProvider 
{
	private String memberFile;//name of member file
	private String billFile;//name of bill file
	
	//ArrayList for members' information reading from member file
	private  ArrayList<Member> memberList = new ArrayList<Member>();
	//ArrayList for bills' information reading from bill file
	private  ArrayList<Bill> billList = new ArrayList<Bill>();
	

	/**
	 * constructor with no parameter
	 */
	public DataProvider()
	{
		
	}
	
	
    /**
     * constructor with 2 parameters
     * It read member file and bill file, then load them into "memberList" and "billList" ArrayList.
     * @param memberFile A path to the member file (e.g., members.csv)
     * @param billFile A path to the bill file (e.g., bills.csv)
     * @throws DataAccessException If a file cannot be opened/read
     * @throws DataFormatException If the format of the the content is incorrect
     * @throws IOException 
     */
     public  DataProvider(String memberFile, String billFile) 
                        throws DataAccessException, DataFormatException
     {
    	 this.memberFile = memberFile;
    	 this.billFile = billFile;
    	 
    	 try//error control
    	 {
    		 BufferedReader memberStream = 
    				 new BufferedReader(new FileReader(memberFile));
    
    		 String l;
    		 //put the member information into "memberList" ArrayList one by one
    		 while((l= memberStream.readLine())!=null)
    		 {
    			 String memberPart[]=l.split(",");
    			 Member member = new Member(memberPart[0],memberPart[1],memberPart[2]);
    			 memberList.add(member);
    		 }
    		 
    		 
    		 BufferedReader billStream = 
    				 new BufferedReader(new FileReader(billFile));
    		 String lines;
    		//put the bill information into "billList" ArrayList one by one
    		 while((lines = billStream.readLine())!=null)
    		 {
    			 String billPart[]=lines.split(",");
    			 double bp = Double.parseDouble(billPart[2]);
    			 boolean bpf = Boolean.valueOf(billPart[3]);
    			 Bill bill = new Bill(billPart[0],billPart[1],bp,bpf);
    			 billList.add(bill);
    		 }
    		 
    	 }
    	 catch(FileNotFoundException e )
    	 {
    		 throw new DataAccessException();
    	 } 
    	 catch (IOException e) 
    	 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
     }
     
    
     /**
      * It can update and overwrite the bill file for the usage of bill.
      * @throws IOException
      */
     public void renewBillFile() throws IOException
     {
    	 BufferedWriter renew = new BufferedWriter (new FileWriter(billFile));
    	 String lines = "";
    	 
    	 //check all bill's "isUsed" parameter
    	 for(Bill bi:billList)
    	 {
    		 String n;
    		 if(bi.isUsed()==true)//the bill has been used
    		 {
    			 n = String.format("%s,%s,%f,true\n",bi.getBillId(),bi.getMemberId(),bi.getAmount());
    		 }
    		 else
    		 {
    			 n=String.format("%s,%s,%f,false\n",bi.getBillId(),bi.getMemberId(),bi.getAmount());
    		 }
    		 lines += n;
    	 }
    	 renew.write(lines);//take the overwrite bills into bill file
    	 renew.close();
     }
     
     
     
     /**
      * This method decides if the input bill id can be found in the bill file 
      * which is "billList" ArrayList here.
      * @param id, the input bill id 
      * @return the bill information related with the input bill id,like member id, amount
      * and the usage tip. 
      */
     public Bill getExistBill(String id)
     {
    	 Bill bill;
    	 
    	 //check all bills
    	 for(int i=0;i<(billList.size());i++)
    	 {
    		 String part = billList.get(i).getBillId();
    		 
    		 //input parameter  is same as the bill id in the Arraylist
    		 if(id.equals(part))
    		{
    			 bill = billList.get(i);
    		     return bill;  
    		}
    	 }
    		 return null;
     }
     
     
     
     /**
      * This method decides if the input bill id has member id in the bill file 
      * which is "billList" ArrayList here. Only the bill with member id can join the competition.
      * @param id, the input bill id 
      * @return the bill information related with the input bill id,like member id, amount
      * and the usage tip. 
      */
     public Bill getUsfulBill(String id)
     {
    	 Bill bill;
    	 
    	 //check all bills
    	 for(int i=0;i<(billList.size());i++)
    	 {
    		 String part = billList.get(i).getBillId();
    		 String memberexist = billList.get(i).getMemberId();
    		 
    		//input parameter  is same as the bill id in the Arraylist 
    		if(id.equals(part))//
    		{
    			 if(!(memberexist==""))//input parameter has member id in the ArrayList
    			 {
    				 bill = billList.get(i);
    				 return bill;
    			 }
    		} 
    	 }
    		 return null;
     }
     
     
     
     /**
      * It is used for gaining the member name for the printing of winners according to 
      * the input member id.
      * @param memid, the input member id
      * @return the member name related with the input member id
      */
     public String getgetMemberName(String memid)
     {
    	 String s;
    	 
    	 //check all members
    	 for(int i=0;i<(memberList.size());i++)
    	 {
    		 Member m = memberList.get(i);
    		 String mid = m.getMemberId();
    		 
    		 //the input parameter is same as the member id in the ArrayList
    		 if(memid.equals(mid))
    		 {
    			 s=m.getMemberName();//the member name related with input parameter
    			 return s;
    		 }
    	 }
    	 return null;
     }
     
     
    
}
