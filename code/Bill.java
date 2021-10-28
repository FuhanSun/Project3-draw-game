import java.io.Serializable;

/*
 * Student name: Fuhan Sun
 * Student ID: 1131339
 * LMS username: fuhans
 */


/**
 * This class is for the information in bill file.
 * It gains bills' id, name amount and usage tip.
 * @author sunfuhan
 * @since 21 June 2021
 */
public class Bill implements Serializable
{
private String billId;
private String memberId;
private double amount;//the amount used for calculating the numbers of entries
private boolean isUsed;//if a bill has been used, the parameter would be true


/**
 * constructor with 4 parameters.
 * @param billId
 * @param memberId
 * @param amount, the amount for the relevant bill 
 * @param isUsed, shows if the bill has used before.
 */
public Bill(String billId, String memberId, double amount,boolean isUsed)
{
	this.billId=billId;
	this.memberId=memberId;
	this.amount = amount;
	this.isUsed=isUsed;
	
}

/**
 * constructor with no parameter.
 */
public Bill()
{
	
}


public void setBillId(String billId)
{
	this.billId = billId;
}

public String getBillId()
{
	return this.billId;
	
}

public void setmemberId(String memberId)
{
	this.memberId=memberId;
}

public String getMemberId()
{
	return this.memberId;
	
}

public double getAmount() 
{
	return amount;
}

public void setIsUsed(boolean isUsed) 
{
	this.isUsed = isUsed;
}

/**
 * The getter for "isUsed" parameter
 * @return true or false for "isUsed" parameter
 */
public boolean isUsed() 
{
	return isUsed;
}

}
