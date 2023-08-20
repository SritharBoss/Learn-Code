package com.main.problems;

import java.util.Scanner;


/**
 * @author SritharBoss
 * @see
 * https://www.hackerearth.com/practice/basic-programming/input-output/basics-of-input-output/practice-problems/algorithm/divisible-or-not-81b86ad7/
 * 
 * **/
public class Divisiblity {
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
        int N = 0;
        N = s.nextInt();
        
        int[] dataArr = new int[N];
        for(int i=0; i<N; i++){
            dataArr[i] = s.nextInt();
        }
        
        
        if(dataArr[dataArr.length-1] %10==0) {
        	System.out.println("Yes");
        }else {
        	System.out.println("No");
        }
        s.close();
        
	}

}
