package com.bharat.corejava.solid;
// wrong design 
//class InterestCalculator {
//	double calculate(String accountType) {
//		if (accountType.equals("SAVINGS"))
//			return 4.0;
//		if (accountType.equals("CURRENT"))
//			return 0.0;
//		return 0;
//	}
//}

interface InterestPolicy {
	double getInterestRate();
}

class SavingsAccountInterest implements InterestPolicy {
	public double getInterestRate() {
		return 4.0;
	}
}

class CurrentAccountInterest implements InterestPolicy {
	public double getInterestRate() {
		return 0.0;
	}
}

class InterestService {
	double calculate(InterestPolicy policy) {
		return policy.getInterestRate();
	}
}

public class OCPBankDemo {
	public static void main(String[] args) {
		InterestService service = new InterestService();

		System.out.println("Savings Interest: " + service.calculate(new SavingsAccountInterest()));

		System.out.println("Current Interest: " + service.calculate(new CurrentAccountInterest()));
	}
}
