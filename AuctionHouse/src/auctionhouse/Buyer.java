package auctionhouse;

public class Buyer {
	private String name;
	private String address;
    private String bankAccount;
    private String bankAuthCode;
	
	public Buyer(String name, String address, String bankAccount, String bankAuth) {
		this.name = name;
		this.address = address;
		this.bankAccount = bankAccount;
		this.bankAuthCode = bankAuth;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public String getBankAuthCode() {
		return bankAuthCode;
	}
	
	
	
	


}
