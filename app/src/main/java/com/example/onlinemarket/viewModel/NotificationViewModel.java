package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinemarket.services.PollWorkManager;

public class NotificationViewModel extends AndroidViewModel {
    int time;
    public NotificationViewModel(@NonNull Application application) {
        super(application);
    }

    public void setupNotification(){
        if (!PollWorkManager.isWorkEnqueued(getApplication())){
            PollWorkManager.enqueue(getApplication(),time,false);

        }else
            PollWorkManager.enqueue(getApplication(),time,true);
    }

    public boolean isWorkEnqueue(){
        return PollWorkManager.isWorkEnqueued(getApplication());
    }
}
