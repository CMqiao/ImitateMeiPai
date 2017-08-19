package com.yqb.imitatemeipai;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by QJZ on 2017/8/18.
 */
@RunWith(AndroidJUnit4.class)
public class VideoTest {

    @Test
    public void getVideoFirstFrame() throws Exception {

        ArrayList<String> videoList = new ArrayList<>();
        videoList.add("http://mvvideo11.meitudata.com/59901c76e28543114_H264_7.mp4");
        videoList.add("http://mvvideo10.meitudata.com/59952ffc9bf8b302_H264_7.mp4");
        videoList.add("http://mvvideo11.meitudata.com/599168acbcedc6158_H264_7.mp4");
        videoList.add("http://mvvideo11.meitudata.com/59917609d05795088_H264_7.mp4");
        videoList.add("http://mvvideo11.meitudata.com/5991724b649c68699_H264_7.mp4");
        videoList.add("http://mvvideo11.meitudata.com/5995410ecc45a64_H264_7.mp4");
        videoList.add("http://mvvideo11.meitudata.com/59954445d885a2082_H264_7.mp4");
        videoList.add("http://mvvideo10.meitudata.com/599190a3cf1a61388_H264_7.mp4");

        MediaMetadataRetriever mmr=new MediaMetadataRetriever();
        mmr.setDataSource(videoList.get(0), new HashMap());
        Bitmap bitmap=mmr.getFrameAtTime();
        saveMyBitmap(bitmap, "01");

    }

    public void saveMyBitmap(Bitmap mBitmap,String bitName)  {
        File f = new File( "", bitName+".jpg");
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
