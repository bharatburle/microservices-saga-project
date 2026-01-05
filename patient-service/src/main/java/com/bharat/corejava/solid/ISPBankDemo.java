package com.bharat.corejava.solid;

//bad design
//interface BankService {
//    void withdraw();
//    void deposit();
//    void issueLoan();
//}

interface DepositService {
	void deposit(double amount);
}

interface WithdrawService {
	void withdraw(double amount);
}

interface LoanService {
	void issueLoan();
}

class SavingsAccountService implements DepositService, WithdrawService {
	public void deposit(double amount) {
		System.out.println("Deposited ₹" + amount);
	}

	public void withdraw(double amount) {
		System.out.println("Withdrawn ₹" + amount);
	}
}

class LoanAccountService implements LoanService {
	public void issueLoan() {
		System.out.println("Loan Issued");
	}
}

public class ISPBankDemo {
	public static void main(String[] args) {
		DepositService account = new SavingsAccountService();
		account.deposit(2000);

		LoanService loan = new LoanAccountService();
		loan.issueLoan();
	}
}
