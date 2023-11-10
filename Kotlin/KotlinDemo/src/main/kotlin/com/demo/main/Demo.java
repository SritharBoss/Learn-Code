package com.demo.main;

import java.util.HashMap;
import java.util.Map;

public class Demo {
    public static void main(String[] args) {
        Map<Integer,String> map = new HashMap<>();
        long start=System.currentTimeMillis();
        for (int i=0;i<10_00_000;i++) {
            map.put(i,String.valueOf(i));
        }
        System.out.println("Time Taken :: "+(System.currentTimeMillis()-start)+"ms");
    }
}
