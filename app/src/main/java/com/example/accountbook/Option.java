
package com.example.accountbook;

public class Option {
    private String aboveText;
    private String belowText;
    public Option(String aboveText, String belowText){
        this.aboveText = aboveText;
        this.belowText = belowText;
    }
    public String getAboveText(){
        return aboveText;
    }
    public String getBelowText(){
        return belowText;
    }
}