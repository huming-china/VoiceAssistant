package com.cloudring.magicsound.model.vmodel;

/**
 * 混合 数据模型基类
 * Created by hm on 2016/9/1.
 */
public class VObject {
    public VObject(String input,String output){
        this.input=input;
        this.output=output;
    }
    public VObject(String input) {
        this.input = input;
        this.output = "对不起我不太明白";
    }
        public VObject(){}
    public String input;

    public String output;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
