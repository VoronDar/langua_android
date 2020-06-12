package com.example.langua.SenteceCheck.Command;

public class Command {
    public final String name;
    public void show(){
        System.out.println(name);
    }
    public void cancel(){
        System.out.println("mmm");
    }
    public Command(String name) {
        this.name = name;
    }
}
