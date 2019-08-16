package com.e.marvelapiproject.customclass;

import android.app.Application;

import com.e.marvelapiproject.di.component.DaggerNetworkComponent;
import com.e.marvelapiproject.di.component.NetworkComponent;
import com.e.marvelapiproject.di.module.RetrofitModule;

public class CustomApplication extends Application {

    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        networkComponent = DaggerNetworkComponent.builder()
                .retrofitModule(new RetrofitModule(Helper.URL))
                .build();
    }

    public NetworkComponent getNetworkComponent(){
        return networkComponent;
    }
}
