package com.main.problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AliAndTruck {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String vNumber = br.readLine();
        System.out.println(getStatus(vNumber));
    }

    private static String getStatus(String vNumber) {
        Pattern pattern = Pattern.compile("([0-9]{2})[A-Z]([0-9]{3})-([0-9]{2})");
        Matcher matcher = pattern.matcher(vNumber);
        if (!matcher.matches()) {
            System.out.println("Not Matched");
            return "";
        }
        for (int i = 1; i <= 3; i++) {
            String match = matcher.group(i);
            char[] chars = match.toCharArray();
            for (int j = 0; j < chars.length - 1; j++) {
                int a = Character.getNumericValue(chars[j]);
                int b = Character.getNumericValue(chars[j + 1]);
                if ((a + b) % 2 == 1) {
                    return "invalid";
                }
            }
        }

        char c=vNumber.charAt(2);
        if(Arrays.asList('A','E','I','O','Y','U').contains(c)){
            return "invalid";
        }
        return "valid";
    }
}
