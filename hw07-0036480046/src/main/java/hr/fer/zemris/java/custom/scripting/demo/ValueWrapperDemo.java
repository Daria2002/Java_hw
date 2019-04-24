package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class ValueWrapperDemo {
	
	public static void main(String[] args) {
		ValueWrapper vv1 = new ValueWrapper(Boolean.valueOf(true));
		vv1.add(Integer.valueOf(5)); // ==> throws, since current value is boolean
		ValueWrapper vv2 = new ValueWrapper(Integer.valueOf(5));
		vv2.add(Boolean.valueOf(true)); // ==> throws, since the argument value is boolean
	}
	
}
