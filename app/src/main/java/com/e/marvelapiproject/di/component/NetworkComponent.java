package com.e.marvelapiproject.di.component;

import com.e.marvelapiproject.di.module.RetrofitModule;
import com.e.marvelapiproject.retrofit.CarregarDadosJSON;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {RetrofitModule.class})
public interface NetworkComponent {
    void inject (CarregarDadosJSON carregarDadosJSON);
}
