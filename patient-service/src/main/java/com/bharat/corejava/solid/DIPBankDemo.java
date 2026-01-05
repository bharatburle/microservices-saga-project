package com.bharat.corejava.solid;

//Bad Design
//class SmsService {
//    void send(String msg) {
//        System.out.println("SMS: " + msg);
//    }
//}
//
//class BankApp {
//    SmsService sms = new SmsService();
//}


interface NotificationService1 {
	void notifyUser(String message);
}

class SmsNotification implements NotificationService1 {
	public void notifyUser(String message) {
		System.out.println("SMS: " + message);
	}
}

class EmailNotification implements NotificationService1 {
	public void notifyUser(String message) {
		System.out.println("Email: " + message);
	}
}

class BankApplication {
	private final NotificationService1 notificationService;

	BankApplication(NotificationService1 notificationService) {
		this.notificationService = notificationService;
	}

	void processTransaction() {
		notificationService.notifyUser("Transaction Successful");
	}
}

public class DIPBankDemo {
	public static void main(String[] args) {
		BankApplication app = new BankApplication(new SmsNotification());

		app.processTransaction();
	}
}
