package com.example.onlinemarket.viewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.di.ContextModule;
import com.example.onlinemarket.services.PollWorkManager;

import javax.inject.Inject;

public class NotificationViewModel extends ViewModel {
    private int time;
    private boolean flag;
    private Context mApplicationContext;
    @Inject
    public NotificationViewModel(ContextModule contextModule) {
        mApplicationContext=contextModule.provideContext();
    }

    public void setupNotification(){
        if (!PollWorkManager.isWorkEnqueued(mApplicationContext)){
            flag=true;
            PollWorkManager.enqueue(mApplicationContext,time,false);

        }else {
            flag=false;
            PollWorkManager.enqueue(mApplicationContext, time, true);
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
