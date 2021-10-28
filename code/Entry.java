import java.io.Serializable;

/*
 * Student name: Fuhan Sun
 * Student ID: 1131339
 * LMS username: fuhans
 */



/**
 * This class is a parent class for "NumbersEntry" and "AutoNumbersEntry" class.
 * It gains the entry id, bill id, member id, and prize for entry.
 * @author sunfuhan
 * @since 18 June 2021
 */
public class Entry implements Serializable
{
    private int entryId;
    private String billId;
    private String memberId;
    private int prize;//prize for entry
    
    
    /**
     * constructor with no parameter
     */
    public Entry()
    {
    	
    }
    
    
    /**
     * constructor with 3 parameters
     * @param entryId
     * @param billId
     * @param memberId
     */
    public Entry(int entryId,String billId,String memberId)
    {
    	this.entryId = entryId;
    	this.billId = billId;
    	this.memberId = memberId;
    }
    
    
    public int getEntryId()
    {
    	return this.entryId;
    }
    
    public String getBid()
    {
    	return this.billId;
    }
    
    public String getMid()
    {
    	return this.memberId;
    }
    
    public int getPrize()
    {
    	return this.prize;
    }
    
    public void setPrize(int prize)
    {
    	this.prize=prize;
    }
}
