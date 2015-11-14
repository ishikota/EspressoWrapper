package com.ikota.espressowrapper.example.data;

import com.ikota.espressowrapper.example.model.Photo;

/**
 * Created by kota on 2015/11/14.
 */
public interface ApiCallback {
   void onResult(Photo photo);
}
