import java.util.ArrayList;
public class Account
{
	private String name;
	private String uuid;
	private User holder;
	private ArrayList<Transaction> transactions;

    //user constructor
    public Account(String name,User holder,Bank  theBank){
    	//setting account holder and user name
    	this.name= name;
    	this.holder=holder;

    	//get uuid for new user account 
    	this.uuid = theBank.getNewAccountuuid();
    	//init Transaction
    	this.transactions = new ArrayList<Transaction>();
    	

    }
    public String getuuid(){
    	return this.uuid;
    }
    public String getSummaryLine(){
    	double balance = this.getBalance();

    	if(balance>=0){
    		return String.format("%s:R%.02f:%s",this.uuid,balance,this.name);
    	}else{
    		return String.format("%s:R%.02f:%s",this.uuid,balance,this.name);
    	}
    }
    public double getBalance(){
    	double balance=0;
    	for(Transaction t: this.transactions){
    		balance+=t.getAmount();
    	}
    	return balance;
    }
    public void printTransHist(){
    	System.out.printf("\nTrans history for account %s\t",this.uuid);
    	for(int t =this.transactions.size()-1;t>=0;t--){
    		System.out.printf(this.transactions.get(t).getSummaryLine());
    	}
    	System.out.println();
    }
    public void addTransaction(double amount, String memo){
    	Transaction newTrans = new Transaction(amount,memo,this);
    	this.transactions.add(newTrans);
    }
}

