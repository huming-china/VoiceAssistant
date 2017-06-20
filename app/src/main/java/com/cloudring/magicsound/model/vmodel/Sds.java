package com.cloudring.magicsound.model.vmodel;
import com.fjtk.musiclib.beans.Data;

public class Sds {
    private String domain;
    private String operation;//": "设置",
    private String object;//": "闹钟",
//    private Nlu nlu;
    private Data data;
    private String state;
    private String contextId;
    private String output;


    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getContextId() {
        return this.contextId;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getOutput() {
        return this.output;
    }

//    public Nlu getNlu() {
//        return nlu;
//    }
//
//    public void setNlu(Nlu nlu) {
//        this.nlu = nlu;
//    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
