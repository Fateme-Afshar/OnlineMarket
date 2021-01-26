package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinemarket.services.PollWorkManager;

public class NotificationViewModel extends AndroidViewModel {
    private int time;
    private boolean flag;
    public NotificationViewModel(@NonNull Application application) {
        super(application);
    }

    public void setupNotification(){
        if (!PollWorkManager.isWorkEnqueued(getApplication())){
            flag=true;
            PollWorkManager.enqueue(getApplication(),time,false);

        }else {
            flag=false;
            PollWorkManager.enqueue(getApplication(), time, true);
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
