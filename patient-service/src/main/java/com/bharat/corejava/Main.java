package com.bharat.corejava;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class FlagExample {
	private volatile boolean running = true;

	public void stop() {
		running = false;
	}

	public void run() {
		while (running) {
			// do work
		}
		System.out.println("Stopped");
	}
}

public class Main {

	public static void main(String[] args) {

//		Map<Integer, String> map = new HashMap<>();
//		map.put(1, "A");
//		map.put(2, "B");
//		map.put(3, "C");
//
//		for (Integer key : map.keySet()) {
//			if (key == 2) {
////				map.put(2, "D");
////				map.put(4, "D");
//			}
//		}
//		System.out.println(map);
//
//		

		System.out.println("threads");
		Map<Integer, String> map = new ConcurrentHashMap<>();

		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				map.put(i, "Value");
			}
		});

		Thread t2 = new Thread(() -> {
			for (Integer key : map.keySet()) {
				System.out.println(key);
			}
		});

		t1.start();
		t2.start();

		System.out.println(map);
//		Employee e1 = new Employee(101);
//		Employee e2 = new Employee(101);
//
//		HashSet<Employee> set = new HashSet<>();
//		set.add(e1);
//		set.add(e2);
//
//		System.out.println("set :: " + set);
//		System.out.println(set.size()); // ✅ 1
//
//		Map<StringBuffer, Integer> map = new HashMap<>();
//		StringBuffer key = new StringBuffer("Bharat");
//		map.put(key, 1);
//		System.out.println(map);
//		key.append("Burle");
//		System.out.println(key);
//		System.out.println(map.get(key));

	}

}

class Employee {
	int id;

	Employee(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Employee))
			return false;
		Employee e = (Employee) o;
		return id == e.id;
	}

	@Override
	public int hashCode() {
		return id;
//        return  (int) System.nanoTime();
//        return 0;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + "]";
	}
}
