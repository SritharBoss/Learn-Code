package com.demo.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Baloons {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCases = Integer.parseInt(br.readLine());

        for(int i=0;i<testCases;i++){
            int[] cost = toIntArr(br.readLine().split(" "));
            int questions = Integer.parseInt(br.readLine());

            int left=0;
            int right=0;
            for(int j=0;j<questions;j++){
                int[] ans = toIntArr(br.readLine().split(" "));
                if(ans[0]==1){
                    left=left+cost[0];
                }
                if(ans[1]==1){
                    right=right+cost[1];
                }
            }

            int result=((left/cost[0])*cost[1])+((right/cost[1])*cost[0]);

            System.out.println(Math.min(result, (left + right)));
        }
    }

    public static int[] toIntArr(String[] value){
        int[] intArr=new int[value.length];
        for(int i=0;i<value.length;i++){
            intArr[i]=Integer.parseInt(value[i]);
        }
        return intArr;
    }
}
