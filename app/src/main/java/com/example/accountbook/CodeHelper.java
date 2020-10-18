package com.example.accountbook;

import java.util.Random;

public class CodeHelper {
    private String codeSet = "0123456789";
    private final int codeSize = 6;
    public String generateCode(){
        Random r = new Random(1);
        String code = null;
        for(int i=0;i<codeSize;++i){
            int j = r.nextInt(10);
            code += codeSet.charAt(j);
        }
        return code;
    }
}
