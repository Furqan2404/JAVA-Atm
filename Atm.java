import java.util.Scanner;
public class Atm{
	public static void main(String[] args){
	//init scanner  
		Scanner sc = new Scanner(System.in);
	//init bank
		Bank theBank = new Bank("Somnahalli");
	//add a user which also creates account
		User  aUser = theBank.addUser("Furquan","Shariff","9090");
	// adding a checking account
		Account newAccount = new Account("Checking",aUser,theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);

		User curUser;
		while(true){
			curUser = Atm.mainMenuPrompt(theBank,sc);
			Atm.printUserMenu(curUser,sc);
		}
	}
	public static User mainMenuPrompt(Bank theBank, Scanner sc){
		String userID;
		String pin;
		User authUser;

		do{
			System.out.printf("\n\nWelcome to the Bank of %s\n\n",theBank.getName());
			System.out.printf("Enter user ID:");
			userID = sc.nextLine();
			System.out.printf("Enter the Pin:");
			pin = sc.nextLine();

			//getting user obj wrt pin and ID
			authUser= theBank.userLogin(userID,pin);
			if(authUser==null){
				System.out.println("Imcorrect user ID/pin combination..plz try again");
			}

		}while(authUser==null);

		return authUser;
	}

	public static void printUserMenu(User theUser,Scanner sc){
		theUser.printAccountsSummary();

		int choice;
		//User menu
		do{
			System.out.printf("Welcome %s , what would you like to do?\n",theUser.getFirstName());
			System.out.println("	1) Show trasaction history");
			System.out.println("	2) Withdrawl");
			System.out.println("	3) Deposit");
			System.out.println("	4) Transfer");
			System.out.println("	5) Quit");
			System.out.println();
			System.out.println("Enter choice:");
			choice = sc.nextInt();

			if(choice < 1 || choice >5){
				System.out.println("Invalid choice please choose between 1-5");
			}

		}while(choice<1||choice>5);
		switch(choice){
		case 1:
			Atm.showTranHist(theUser,sc);
			break;
		case 2:
			Atm.WithdrawlFunds(theUser,sc);
			break;
		case 3:
			Atm.depositFunds(theUser,sc);
			break;
		case 4:
			Atm.transFund(theUser,sc);
			break;
		}
		if(choice!=5){
			Atm.printUserMenu(theUser,sc);
		}
	}
	public static void showTranHist(User theUser,Scanner sc){
		int theAcct;
		do{
			System.out.printf("Enter the number (1-%d) of the account\n"+"Whose transactions you want to see:",theUser.numAccounts());
			theAcct= sc.nextInt()-1;
			if(theAcct<0 || theAcct>=theUser.numAccounts()){
				System.out.println("Invalid account please  try again later");
			}

		}while(theAcct <0|| theAcct  >= theUser.numAccounts());
		//printing transaction history
		theUser.printAccTransHist(theAcct);
	}
	public  static void transFund(User theUser,Scanner sc){
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		//get acccount transfer from
		do{
			System.out.printf("Enter the number (1-%d)of the accounts\n"+"to transfer from:",theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct<0 || fromAcct>=theUser.numAccounts()){
				System.out.println("Invalid account please  try again later"); 
			}

		}while(fromAcct < 0 || fromAcct >=theUser.numAccounts());
		acctBal = theUser.getAcctBal(fromAcct);

		//get account to transfer to 
		do{
			System.out.printf("Enter the number (1-%d)of the accounts\n"+"to transfer to:",theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if(toAcct<0 || toAcct>=theUser.numAccounts()){
				System.out.println("Invalid account please  try again later"); 
			}

		}while(toAcct < 0 || toAcct >=theUser.numAccounts());

		//get amount to transfer
		do{
			System.out.printf("Enter the amount to transfer (max R%0.2f):R",acctBal);
			amount=sc.nextDouble();
			if(amount<0){
				System.out.println("Amount must be  greater than  zero.");
			}else if(amount>acctBal){
				System.out.printf("Amount must not be greater than\n"+"balance of R%0.2f.\n",acctBal);
			}
		}while(amount<0||amount>acctBal);
		//finally tranfer
		theUser.addAcctTransaction(fromAcct,-1*amount,String.format(
			"Transfer to account %s",theUser.getAcctuuid(toAcct)));
		theUser.addAcctTransaction(toAcct,amount,String.format(
			"Transfer to account %s",theUser.getAcctuuid(fromAcct)));
	}
	//making withdrawl method
	public static void WithdrawlFunds(User theUser,Scanner sc){
		int fromAcct;
		double amount;
		double acctBal;
		String memo;
		//get acccount transfer from
		do{
			System.out.printf("Enter the number (1-%d)of the accounts\n"+"to withdrawl from:",theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct<0 || fromAcct>=theUser.numAccounts()){
				System.out.println("Invalid account please  try again later"); 
			}

		}while(fromAcct < 0 || fromAcct >=theUser.numAccounts());
		acctBal = theUser.getAcctBal(fromAcct);

		//getting amount to transfer
		do{
			System.out.printf("Enter the amount to Withdraw (max R%.02f):R",acctBal);
			amount=sc.nextDouble();
			if(amount<0){
				System.out.println("Amount must be  greater than  zero.");
			}else if(amount>acctBal){
				System.out.printf("Amount must not be greater than\n"+"balance of R%.02f.\n",acctBal);
			}
		}while(amount<0||amount>acctBal);
		sc.nextLine();
		System.out.printf("Enter a memo:");
		memo = sc.nextLine();

		//finally withdrawing
		theUser.addAcctTransaction(fromAcct,-1*amount,memo);
	}

	public static void  depositFunds(User theUser,Scanner sc){
		int toAcct;
		double amount;
		double acctBal;
		String memo;
		//get acccount transfer from
		do{
			System.out.printf("Enter the number (1-%d)of the accounts\n"+"to deposit in:",theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if(toAcct<0 || toAcct>=theUser.numAccounts()){
				System.out.println("Invalid account please  try again later"); 
			}

		}while(toAcct < 0 || toAcct >=theUser.numAccounts());
		acctBal = theUser.getAcctBal(toAcct);

		//getting amount to transfer
		do{
			System.out.printf("Enter the amount to  deposit (max R%.02f):R",acctBal);
			amount=sc.nextDouble();
			if(amount<0){
				System.out.println("Amount must be  greater than  zero.");
			}
		}while(amount<0);
		sc.nextLine();
		System.out.printf("Enter a memo:");
		memo = sc.nextLine();

		//finally withdrawing
		theUser.addAcctTransaction(toAcct,amount,memo);
	}
}
//1:22:44