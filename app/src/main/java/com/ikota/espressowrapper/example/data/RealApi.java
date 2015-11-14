package com.ikota.espressowrapper.example.data;


import android.os.AsyncTask;
import android.os.SystemClock;

import com.ikota.espressowrapper.example.model.Photo;

public class RealApi implements Api{
    @Override
    public void callAPi(ApiCallback callback) {
        new RealApiCall(callback).execute();
    }

    private static class RealApiCall extends AsyncTask<Void, Void, Photo> {
        private ApiCallback mCallback;
        public RealApiCall(ApiCallback callback) {
            mCallback = callback;
        }

        @Override
        protected Photo doInBackground(Void... voids) {
            SystemClock.sleep(3000);  // simulate api call
            return Photo.createFakeData();
        }

        @Override
        protected void onPostExecute(Photo photo) {
            mCallback.onResult(photo);
        }
    }
}
