package com.main.problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SplitHouses {
	static String result = "";

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String num1 = br.readLine();
		int num = Integer.valueOf(num1);

		String village = br.readLine();
		String[] grid = village.split("");
		
		for (int i = 0; i < num - 1; i++) {

			if (grid[i].equals("H") && grid[i + 1].equals("H")) {
				result="NO";
				System.out.println("NO");
				break;
			} else {
				if(grid[i].equals(".")) {
					grid[i]="B";
				}
				
			}
		}
		
		if(grid[num-1].equals(".")) {
			grid[num-1]="B";
		}
		
		if(!result.equals("NO")) {
			System.out.println("Yes");
			for(String s:grid) {
				System.out.print(s);
			}
		}
	}
}
