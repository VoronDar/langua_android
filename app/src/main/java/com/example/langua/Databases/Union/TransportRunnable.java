package com.example.langua.Databases.Union;

import android.content.Context;

public interface TransportRunnable {
    void onSomethingGoesWrong(int messageId);
    void onSuccess();
    Context getContext();
}
