package com.example.onlinemarket.services;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.onlinemarket.utils.ProgramUtils;
import com.example.onlinemarket.utils.ServiceUtils;

import java.util.concurrent.TimeUnit;

public class PollWorkManager extends Worker {

    public PollWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ServiceUtils.checkLastProductId(getApplicationContext());
        return Result.success();
    }

    public static void enqueue(Context context, int hour,boolean isOn) {
        WorkManager workManager = WorkManager.getInstance(context.getApplicationContext());

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresCharging(true)
                .build();

            PeriodicWorkRequest workRequest = new PeriodicWorkRequest.
                    Builder(PollWorkManager.class,
                    hour,
                    TimeUnit.MINUTES).
                    setConstraints(constraints).
                    build();
        if (!isOn){
            Log.d(ProgramUtils.TAG,"PollWorkManager : start schedule task");
            workManager.enqueueUniquePeriodicWork(
                    PollWorkManager.class.getSimpleName(),
                    ExistingPeriodicWorkPolicy.KEEP,
                    workRequest);
        }else {
            Log.d(ProgramUtils.TAG,"PollWorkManager : stop schedule task");
            workManager.cancelUniqueWork(PollWorkManager.class.getSimpleName());
        }
    }
}
