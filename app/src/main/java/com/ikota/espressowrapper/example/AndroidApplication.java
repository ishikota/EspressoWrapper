package com.ikota.espressowrapper.example;

import android.app.Application;
import android.util.Log;

import com.ikota.espressowrapper.example.data.Api;
import com.ikota.espressowrapper.example.di.RealApiModule;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

public class AndroidApplication extends Application {

    private ObjectGraph objectGraph = null;

    @Inject
    Api api;

    @Override
    public void onCreate() {
        super.onCreate();
        if(objectGraph == null) {
            Log.d("AndroidApplication", "Use RealApi");
            List modules = Collections.singletonList(new RealApiModule());
            objectGraph = ObjectGraph.create(modules.toArray());
        }
    }

    // used to set ObjectGraph for test
    public void setObjectGraph(ObjectGraph graph) {
        objectGraph = graph;
    }

    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }

    public Api api() {
        return api;
    }
}
