package com.nathanruff.baseconverter;

public class Formater {

	public static int[] main(String expression) {
		int[] order = new int[3];
		expression = expression.trim();
		order[0] = Integer.valueOf(expression.substring(0, expression.indexOf("[")).trim());
		order[1] = Integer.valueOf(expression.substring(expression.indexOf("[")+1, expression.indexOf("]")).trim());
		order[2] = Integer.valueOf(expression.substring(expression.indexOf(",")+1).trim());
		return order;
	}

}
