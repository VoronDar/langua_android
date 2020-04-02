package com.example.langua.activities.statistics.command;

import android.content.Context;

public interface Command {
    public abstract void action(Context context);
    public abstract void drop(Context context);
    public abstract String getName();

}
