package com.ikota.espressowrapper.example.di;

import com.ikota.espressowrapper.example.AndroidApplication;
import com.ikota.espressowrapper.example.data.Api;
import com.ikota.espressowrapper.example.data.RealApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = AndroidApplication.class,
        library = true
)
public class RealApiModule {

    @Provides
    @Singleton
    public Api provideRealApi() {
        return new RealApi();
    }
}
