package com.main.util;

import java.io.*;

public class Util {
    public static String readFromFile(String inputFile,Integer lineNo){
        StringBuilder sb=new StringBuilder();
        try {
            BufferedReader br=new BufferedReader(new FileReader(inputFile));
            String line;
            int i=0;
            while((line=br.readLine())!=null){
                if(lineNo!=null && i==lineNo){
                    return line;
                }else{
                    sb.append(line).append(System.lineSeparator());
                }
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
}
