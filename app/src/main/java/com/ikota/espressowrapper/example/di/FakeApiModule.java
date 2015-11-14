package com.ikota.espressowrapper.example.di;

import com.ikota.espressowrapper.example.AndroidApplication;
import com.ikota.espressowrapper.example.data.Api;
import com.ikota.espressowrapper.example.data.FakeApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = AndroidApplication.class,
        library = true
)
public class FakeApiModule {

    @Provides
    @Singleton
    public Api provideFakeApi() {
        FakeApi api = new FakeApi();
        api.setSleepingTime(1);
        return api;
    }
}
