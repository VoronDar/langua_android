package com.example.langua.activities.statistics;


import android.content.Context;

import com.example.langua.activities.statistics.command.Command;

import java.util.ArrayList;

public class statistics {
    private ArrayList<Command> commands;                                                            // queue with commands
    private static statistics instance;                                                             // the object itself
    private Context context;


    //////// LIFE CYCLE ////////////////////////////////////////////////////////////////////////////
    private statistics(){
        commands = new ArrayList<>();
    }
    public static statistics getInstance(Context context){
        if (instance == null)
            instance = new statistics();
        instance.context = context;
        return instance;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    //////// COMMAND USE ///////////////////////////////////////////////////////////////////////////
    public void putCommand(Command command){
        instance.commands.add(command);
    }
    public void executeCommand(){
        instance.commands.get(0).action(instance.context);
        instance.commands.remove(0);
    }
    public void cancelComand(){
        instance.commands.get(0).drop(instance.context);
        instance.commands.remove(0);
    }
    public String getActionName(){                                                                  // get string command for user
        return instance.commands.get(0).getName();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////



    public boolean isEmpty(){
        return (instance.commands.size() == 0);
    }
}
