package com.weatherforecast.android.ui.media;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.ArrayList;

public class MediaViewModel extends ViewModel {
    public ArrayList<String> mediaList = new ArrayList<String>();
    public ArrayList<String> updateSongManage(String Path){
        mediaList.clear();
        File file = new File(Path);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File item : files) {
                    if (item.getName().endsWith(".mp3")) {
                        mediaList.add(item.getName());
                    }
                }
            }
        }
        return mediaList;
    }
}
