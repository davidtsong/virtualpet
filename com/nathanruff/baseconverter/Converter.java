package com.nathanruff.baseconverter;
import java.lang.Math;

public class Converter {

	public static String main(int[] order) {
		String newNumber = "";
		int decNum = 0;
		for (int count = 0; count < String.valueOf(order[0]).length(); count++) {
			decNum+=(Math.pow(order[1], (count)))*Integer.valueOf(String.valueOf(order[0]).substring(String.valueOf(order[0]).length()-1-count, String.valueOf(order[0]).length()-count));
		}
		
		int digits = (int) (Math.log(decNum)/Math.log(order[2])+1);
		int[] newDigitList = new int[digits];
		int newDN = decNum;
		//System.out.print(" = ");
		int newDigits = digits;
		
		for (int count = 0; count < digits; count++) {
			
			
			if (digits-count != newDigits) {
				newDigitList[count] = 0;
			} else {
				
				newDigitList[count] = (int) (newDN / Math.pow(order[2], (digits-1-count)));
				newDN = (int) ((newDN % (Math.pow(order[2], (digits-1-count)))));
				newDigits = (int) (Math.log(newDN)/Math.log(order[2])+1);
			}
			if (Integer.valueOf(newDigitList[count]) >= 0 && Integer.valueOf(newDigitList[count]) <= 9) {
				newNumber += newDigitList[count];
			} else if (Integer.valueOf(newDigitList[count]) > 9) {
				newNumber += (char) (Integer.valueOf(newDigitList[count])-10+'A');
			}
			//System.out.print(newDigitList[count]);
		}
		//System.out.println(" Base "+ order[2]);
		return newNumber+"["+order[2]+"]";

	}

}
