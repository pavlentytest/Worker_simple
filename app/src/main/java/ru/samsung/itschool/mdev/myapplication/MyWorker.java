package ru.samsung.itschool.mdev.myapplication;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    public final String TAG = "MY_TAG";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String s = getInputData().getString("keyA");
        int i = getInputData().getInt("keyB", 0);
        Log.d(TAG,"s="+s);

        Log.v(TAG, "Work is in progress");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Work finished");
        Data output = new Data.Builder()
                .putString("keyC", "Hello")
                .putInt("keyD", 10)
                .build();
        return Worker.Result.success(output);
    }
}