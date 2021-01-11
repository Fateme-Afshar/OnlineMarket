package com.example.onlinemarket.services;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.onlinemarket.utils.ProgramUtils;
import com.example.onlinemarket.utils.ServiceUtils;

import java.util.List;
import java.util.concurrent.ExecutionException;
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
                .build();

            PeriodicWorkRequest workRequest = new PeriodicWorkRequest.
                    Builder(PollWorkManager.class,
                    hour,
                    TimeUnit.HOURS).
                    setConstraints(constraints).
                    build();
        if (!isOn){
            Log.d(ProgramUtils.TAG,"PollWorkManager :enqueue task");
            workManager.enqueueUniquePeriodicWork(
                    PollWorkManager.class.getSimpleName(),
                    ExistingPeriodicWorkPolicy.REPLACE,
                    workRequest);
        }else {
            Log.d(ProgramUtils.TAG,"PollWorkManager : stop enqueue task");
            workManager.cancelUniqueWork(PollWorkManager.class.getSimpleName());
        }
    }

    public static boolean isWorkEnqueued(Context context) {
        WorkManager workManager = WorkManager.getInstance(context);
        try {
            List<WorkInfo> workInfos =
                    workManager.getWorkInfosForUniqueWork(PollWorkManager.class.getSimpleName()).get();

            for (WorkInfo workInfo: workInfos) {
                if (workInfo.getState() == WorkInfo.State.ENQUEUED ||
                        workInfo.getState() == WorkInfo.State.RUNNING)
                    return true;
            }

            return false;
        } catch (ExecutionException e) {
            Log.e(ProgramUtils.TAG, e.getMessage(), e);
            return false;
        } catch (InterruptedException e) {
            Log.e(ProgramUtils.TAG, e.getMessage(), e);
            return false;
        }
    }
}
