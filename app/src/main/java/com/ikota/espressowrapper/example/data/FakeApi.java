package com.ikota.espressowrapper.example.data;

import android.os.AsyncTask;
import android.os.SystemClock;

import com.ikota.espressowrapper.example.model.Photo;


public class FakeApi implements Api {

    private int mSleepingTime = 3000;

    public void setSleepingTime(int seconds) {
        this.mSleepingTime = seconds;
    }

    @Override
    public void callAPi(ApiCallback callback) {
        new FakeApiCall(callback).execute(mSleepingTime);
    }

    private static class FakeApiCall extends AsyncTask<Integer, Void, Photo> {
        private ApiCallback mCallback;
        public FakeApiCall(ApiCallback callback) {
            mCallback = callback;
        }

        @Override
        protected Photo doInBackground(Integer... args) {
            SystemClock.sleep(args[0]);  // simulate api call
            return Photo.createFakeData();
        }

        @Override
        protected void onPostExecute(Photo photo) {
            photo.title = "FakeTitle";
            mCallback.onResult(photo);
        }
    }

}
