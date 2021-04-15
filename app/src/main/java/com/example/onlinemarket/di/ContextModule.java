package com.example.onlinemarket.di;

import android.content.Context;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private final Context mContext;

    @Inject
    public ContextModule(Context context) {
        mContext = context;
    }

    @Provides
    public Context provideContext(){
        return mContext;
    }
}
