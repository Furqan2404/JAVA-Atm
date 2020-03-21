import  java.util.ArrayList;
import  java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class User 
{
	private String FirstName;
	private String LastName;
	private String uuid;
	private byte pinHash[];
	private ArrayList<Account> accounts;
	// Creating a constructor
	public User(String FirstName,String LastName,String pin,Bank theBank){
		// setting user details
		this.FirstName=FirstName;
		this.LastName=LastName;

		//Hashing our pin using MD5 algorithm
		//security reasons
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException  e){
			System.err.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		//get a new unique id
		this.uuid = theBank.getNewUserId();
		//create empty list of acounts
		this.accounts= new ArrayList<Account>();
		//print log message
		System.out.printf("New user %s , %s with Id %s is created .\n",LastName,FirstName,this.uuid);
	}
	public void addAccount(Account anAcct){
		this.accounts.add(anAcct);
	}
	public String getuuid(){
		return this.uuid;
	}
	public boolean validatePin(String aPin){
		try{
			MessageDigest md= MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()),this.pinHash);
		}catch(NoSuchAlgorithmException e){
			System.out.println("error caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);

		}
		return false;

	}
	public String getFirstName(){
		return this.FirstName;
	}
	public void printAccountsSummary(){
		System.out.printf("\n\n%s's Account summary\n",this.FirstName);
		for(int a=0;a<this.accounts.size();a++){
			System.out.printf("%d)%s\n",a+1,this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
	}
	public  int numAccounts(){
		return this.accounts.size();
	}
	public void  printAccTransHist(int accIds){
		this.accounts.get(accIds).printTransHist();
	}
	public double getAcctBal(int acctIds){
		return  this.accounts.get(acctIds).getBalance();

	}
	public String getAcctuuid(int acctIds){
		return  this.accounts.get(acctIds).getuuid();
	}
	public void addAcctTransaction(int acctIds,double amount,String memo){
		this.accounts.get(acctIds).addTransaction(amount,memo);

	}

}
