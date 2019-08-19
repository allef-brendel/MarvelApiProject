package com.e.marvelapiproject.dagger.component;

import com.e.marvelapiproject.dagger.module.AppModule;
import com.e.marvelapiproject.dagger.module.RetrofitModule;
import com.e.marvelapiproject.retrofit.CarregarDadosJSON;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {RetrofitModule.class, AppModule.class})
public interface NetworkComponent {
    void inject (CarregarDadosJSON carregarDadosJSON);
}
