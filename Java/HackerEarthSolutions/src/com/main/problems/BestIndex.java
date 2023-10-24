package com.main.problems;

import com.main.util.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Srithar
 * @link <a href="https://www.hackerearth.com">Hackerearch Solution</a>
**/
public class BestIndex {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //int lineCount = Integer.parseInt(br.readLine());
        int lineCount = Integer.parseInt(Util.readFromFile("/mnt/Data/Downloads/87957f1ea6d711e8.txt.clean.txt",0));
        int[] intArr = new int[lineCount];
        //String[] line = br.readLine().split("\\s+");
        String[] line = Util.readFromFile("/mnt/Data/Downloads/87957f1ea6d711e8.txt.clean.txt",1).split("\\s+");
        for (int i = 0; i < lineCount; i++) {
            intArr[i] = Integer.parseInt(line[i]);
        }

        long startTime=System.nanoTime();
        long[] specialValue = new long[lineCount];
        for (int i = 0; i < lineCount; i++) {
            int counter = 1;
            long sum = 0;
            int available=lineCount-i;
            while(counter<=available) {
                for (int k = 0; k < counter; k++) {
                    sum = sum + intArr[lineCount-available];
                    available--;
                }
                counter++;
            }
            specialValue[i] = sum;
        }

        long maxValue = specialValue[0];

        for (long j : specialValue) {
            if (j > maxValue) {
                maxValue = j;
            }
        }
        long endTime=System.nanoTime();
        System.out.println((double)(endTime-startTime)/1000000000);
        System.out.println(maxValue);
    }
}
