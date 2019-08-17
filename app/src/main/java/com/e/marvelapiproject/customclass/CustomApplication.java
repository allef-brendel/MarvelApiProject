package com.e.marvelapiproject.customclass;

import android.app.Application;

import com.e.marvelapiproject.di.component.DaggerNetworkComponent;
import com.e.marvelapiproject.di.component.NetworkComponent;
import com.e.marvelapiproject.di.module.AppModule;
import com.e.marvelapiproject.di.module.RetrofitModule;

public class CustomApplication extends Application {

    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        networkComponent = DaggerNetworkComponent.builder()
                .appModule(new AppModule(this))
                .retrofitModule(new RetrofitModule("http://gateway.marvel.com/v1/public/"))
                .build();
    }

    public NetworkComponent getNetworkComponent(){
        return networkComponent;
    }
}
