package com.e.marvelapiproject.dagger.customclass;

import android.app.Application;

import com.e.marvelapiproject.dagger.component.DaggerNetworkComponent;
import com.e.marvelapiproject.dagger.component.NetworkComponent;
import com.e.marvelapiproject.dagger.module.AppModule;
import com.e.marvelapiproject.dagger.module.RetrofitModule;

public class ModuleApplication extends Application {

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
