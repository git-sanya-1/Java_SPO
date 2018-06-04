package StackMachine;

import HashSet.HashSet;
import LinkedList.LinkedList;


public class Variable {
    private float value;
    private String type;
    private LinkedList list;
    private HashSet hset;

    Variable(String type, int value) {
        this.type = type;
        this.value = value;
    }

    Variable(String type, LinkedList list) {
        this.type = type;
        this.list=list;
    }
    Variable(String type, HashSet hashSet){
        this.type=type;
        this.hset=hashSet;
    }

    float getValue() {
        return value;
    }
    LinkedList getList(){
        return list;}

    public String getType() {
        return type;
    }

    void set_func(float value) {
        this.value = value;
    }
    HashSet getSet(){return hset;}
}