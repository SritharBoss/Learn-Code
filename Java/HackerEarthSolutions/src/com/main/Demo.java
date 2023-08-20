package com.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Demo {

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		String villages = br.readLine();
		int ispossible = 0;
		int counterGrids = 0;
		int counterGridsAux = 0;
		String villageFences = villages.replace('.', 'B');
		char prevGrid = '0';

		for (int i = 0; i < n; i++) {
			if (villageFences.charAt(i) == 'H') {
				if (prevGrid == 'H') {
					ispossible = 0;
					System.out.println("NO");
					break;
				}
				prevGrid = 'H';
				counterGridsAux++;
				ispossible = 1;

			} else {
				counterGridsAux = 0;
			}
			prevGrid = villageFences.charAt(i);

		}
		if (counterGrids == counterGridsAux) {
			ispossible = 1;
		}
		if (ispossible == 1) {
			System.out.println("YES");
			System.out.println(villageFences);
		}

	}
}