package com.main.problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SevenSegment {

    private static final Map<Integer,Integer> map=new HashMap<>();

    static{
        map.put(0,6);
        map.put(1,2);
        map.put(2,5);
        map.put(3,5);
        map.put(4,4);
        map.put(5,5);
        map.put(6,6);
        map.put(7,3);
        map.put(8,7);
        map.put(9,6);
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCases = Integer.parseInt(br.readLine());

        for(int i=0;i<testCases;i++){
            String input=br.readLine();
            char[] inputArr=input.toCharArray();
            int sticksCount=0;
            for(char a:inputArr){
                sticksCount=sticksCount+map.get(Character.getNumericValue(a));
            }
            StringBuilder sb=new StringBuilder();
            if(sticksCount%2==0){
                while(sticksCount!=0){
                    sb.append("1");
                    sticksCount=sticksCount-2;
                }
                System.out.println(sb);
            }else if(sticksCount%2==1){
                sticksCount=sticksCount-3;
                sb.append("7");
                while(sticksCount!=0){
                    sb.append("1");
                    sticksCount=sticksCount-2;
                }
                System.out.println(sb);
            }
        }
    }
}
