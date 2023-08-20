package com.main.problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NoOfSteps {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(br.readLine());
        int[] a = getIntArray(br.readLine().split(" "));
        int[] b = getIntArray(br.readLine().split(" "));

        int count=0;
        int minValue=getLeastNum(a);
        int i=0;
        while(i<size) {
            while (a[i] > minValue) {
                a[i] = a[i] - b[i];
                count++;
            }
            if (a[i] < minValue) {
                minValue = a[i];
                i = 0;
            } else if (a[i] < 0) {
                count = -1;
                break;
            } else {
                i++;
            }
        }
        System.out.println(count);

    }

    public static int getLeastNum(int[] a) {
        int result = a[0];
        for (int s : a) {
            if (result > s) {
                result = s;
            }
        }
        return result;
    }

    public static int[] getIntArray(String[] a) {
        int[] b = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = Integer.parseInt(a[i]);
        }
        return b;
    }
}
