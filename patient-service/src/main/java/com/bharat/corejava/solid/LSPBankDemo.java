package com.bharat.corejava.solid;

//wrong design

//class Account {
//    void withdraw(double amount) {
//        System.out.println("Withdrawn " + amount);
//    }
//}
//
//class FixedDepositAccount extends Account {
//    void withdraw(double amount) {
//        throw new RuntimeException("Withdrawal not allowed");
//    }
//}

interface Account {
	void deposit(double amount);
}

interface WithdrawableAccount extends Account {
	void withdraw(double amount);
}

class SavingsAccount implements WithdrawableAccount {
	public void deposit(double amount) {
		System.out.println("Deposited ₹" + amount);
	}

	public void withdraw(double amount) {
		System.out.println("Withdrawn ₹" + amount);
	}
}

class FixedDepositAccount implements Account {
	public void deposit(double amount) {
		System.out.println("FD Created with ₹" + amount);
	}
}

public class LSPBankDemo {
	public static void main(String[] args) {
		WithdrawableAccount savings = new SavingsAccount();
		savings.withdraw(1000);

		Account fd = new FixedDepositAccount();
		fd.deposit(50000);
	}
}
