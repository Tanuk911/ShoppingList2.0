package com.example.activityonesqlite.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorUtility {
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    private ExecutorUtility(){

    }
    public static void runOnBackgroundThread(Runnable task) {
        executorService.execute(task);
    }
    public static void runOnMainThread(Runnable task) {
        mainHandler.post(task);
    }

    public static void runWithCallback(Runnable backgroundTask, Runnable callbackTask){
        executorService.execute(() -> {
            backgroundTask.run();
            mainHandler.post(callbackTask);
        });
    }
}
