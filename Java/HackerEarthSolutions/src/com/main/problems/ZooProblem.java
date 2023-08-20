package com.main.problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ZooProblem {
	public static void main(String args[] ) throws Exception {
		
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = br.readLine();
        while(name.toLowerCase().contains("zoo")){
        	name=name.toLowerCase().replace("zoo", "");
        }
        if(name.isEmpty()) {
        	System.out.println("Yes");
        }else {
        	System.out.println("No");
        }
    }
}
