package com.example.langua.SenteceCheck.Command;

public class OrderCommand extends Command{
        public OrderCommand(String name) {
            super(name);
        }
        @Override
        public void show() {
            System.out.println(name);
        }
}
