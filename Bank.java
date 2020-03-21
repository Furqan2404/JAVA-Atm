import java.util.ArrayList;
import java.util.Random;

public class Bank {
	private String name;
	private ArrayList<User> users;
	private ArrayList<Account> accounts;
	
	public Bank(String name){
		this.name=name;
		this.users = new ArrayList<User>();
		this.accounts= new ArrayList<Account>();
	}

	public String getNewUserId(){
		//inits
		String uuid;
		Random rng = new Random();
		int len=6;
		boolean nonunique=false;
		//contunue looping until unique  ID is generated
		do{
			//generate a number
			uuid="";
			for(int c=0;c<len;c++){
				uuid +=((Integer)rng.nextInt(10)).toString();
			}
			//check
			nonunique=false;
			for(User u:this.users){
				if(uuid.compareTo(u.getuuid())==0){
					nonunique=true;
					break;
				}
			}

		}while(nonunique);
		
		return uuid;

	}
	public String getNewAccountuuid(){
		//inits
		String uuid;
		Random rng = new Random();
		int len=10;
		boolean nonunique=false;
		//contunue looping until unique  ID is generated
		do{
			//generate a number
			uuid="";
			for(int c=0;c<len;c++){
				uuid +=((Integer)rng.nextInt(10)).toString();
			}
			//check
			nonunique=false;
			for(Account a:this.accounts){
				if(uuid.compareTo(a.getuuid())==0){
					nonunique=true;
					break;
				}
			}

		}while(nonunique);
		
		return uuid;

	}
	public void addAccount(Account anAcct){
		this.accounts.add(anAcct);
	}
	
	public User addUser(String FirstName,String Lastname,String pin){
		User newUser = new User(FirstName,Lastname,pin,this);
		this.users.add(newUser);

		//creating a savings account
		Account newAccount= new Account("Savings",newUser,this);
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);
		return newUser;
	}
	public User userLogin(String userID,String pin){
		//searching in user 
		for(User u:this.users){
			//if user found
			if(u.getuuid().compareTo(userID)==0&&u.validatePin(pin)){
				return u;
			}
		}
		//if not found 
		return null;
	}
	public String getName(){
		return this.name;
	}
}



