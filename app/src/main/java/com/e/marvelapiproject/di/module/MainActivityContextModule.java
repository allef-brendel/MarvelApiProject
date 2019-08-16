package com.e.marvelapiproject.di.module;

import android.content.Context;

import com.e.marvelapiproject.MainActivity;
import com.e.marvelapiproject.di.qualifier.ActivityContext;
import com.e.marvelapiproject.di.scopes.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityContextModule {

    private MainActivity mainActivity;
    public Context context;

    public MainActivityContextModule(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        context = mainActivity;
    }

    @Provides
    @ActivityScope
    public MainActivity providesMainActivity(){
        return mainActivity;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context providesContext(){
        return context;
    }

}
