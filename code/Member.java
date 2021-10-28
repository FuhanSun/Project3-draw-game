/*
 * Student name: Fuhan Sun
 * Student ID: 1131339
 * LMS username: fuhans
 */


/**
 * This class is for the information in member file.
 * It gains members' id, name and email.
 * @author sunfuhan
 * @since 21 June 2021
 */
public class Member 
{
private String memberId;
private String memberName;
private String memberEmail;


/**
 * constructor with 3 parameters.
 * @param memberId
 * @param memberName
 * @param memberEmail
 */
public Member(String memberId,String memberName,String memberEmail)
{
	this.memberId=memberId;
	this.memberName=memberName;
	this.memberEmail=memberEmail;
}


/**
 * constructor with no parameter.
 */
public Member()
{
	
}



public String getMemberId() {
	return memberId;
}

public String getMemberName() {
	return memberName;
}

public String getMemberAddress() {
	return memberEmail;
}


}
