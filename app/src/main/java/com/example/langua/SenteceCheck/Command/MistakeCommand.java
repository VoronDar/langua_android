package com.example.langua.SenteceCheck.Command;

public class MistakeCommand extends Command{
    public final int mistakeIndex;
    public final String correctWriting;

    public MistakeCommand(String name, int mistakeIndex, String correctWriting) {
        super(name);
        this.mistakeIndex = mistakeIndex;
        this.correctWriting = correctWriting;
    }

    @Override
    public void show() {
        System.out.println(name);
    }
}