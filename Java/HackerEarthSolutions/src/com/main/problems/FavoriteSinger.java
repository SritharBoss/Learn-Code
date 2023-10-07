package com.main.problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
public class FavoriteSinger {
    /***
     @link https://www.hackerearth.com/practice/basic-programming/input-output/basics-of-input-output/practice-problems/algorithm/favourite-singer-a18e086a
    */
    public static void main(String args[] ) throws Exception {
        //BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        String[] input = br.readLine().split(" ");
        Map<String,Integer> map=new HashMap<>();
        for(String str:input){
            if(!map.containsKey(str)){
                map.put(str,1);
            }else{
                map.put(str,map.get(str)+1);
            }
        }
        int val=Collections.max(map.values());
        System.out.println(map.values().stream().filter(a->a==val).count());
    }
}
