package com.bharat.corejava.solid;

//wrong design 
//class BankAccount {
//  void deposit(double amount) {
//      System.out.println("Depositing " + amount);
//  }
//
//  void sendSmsAlert() {
//      System.out.println("Sending SMS alert");
//  }
//}

class BankAccount {
	void deposit(double amount) {
		System.out.println("Deposited ₹" + amount);
	}
}

class NotificationService {
	void sendSms(String message) {
		System.out.println("SMS Sent: " + message);
	}
}

public class SRPBankDemo {
	public static void main(String[] args) {
		BankAccount account = new BankAccount();
		NotificationService notification = new NotificationService();

		account.deposit(5000);
		notification.sendSms("₹5000.00 credited");
	}
}
