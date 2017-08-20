package com.yqb.imitatemeipai;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;

import com.yqb.imitatemeipai.util.FileUtil;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        ArrayList<String> videoList = new ArrayList<>();
        videoList.add("http://mvvideo11.meitudata.com/59901c76e28543114_H264_7.mp4");
        videoList.add("http://mvvideo10.meitudata.com/59952ffc9bf8b302_H264_7.mp4");
        videoList.add("http://mvvideo11.meitudata.com/599168acbcedc6158_H264_7.mp4");
        videoList.add("http://mvvideo11.meitudata.com/59917609d05795088_H264_7.mp4");
        videoList.add("http://mvvideo11.meitudata.com/5991724b649c68699_H264_7.mp4");
        videoList.add("http://mvvideo11.meitudata.com/59954445d885a2082_H264_7.mp4");
        videoList.add("http://mvvideo10.meitudata.com/599190a3cf1a61388_H264_7.mp4");

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource("http://mvvideo11.meitudata.com/59901c76e28543114_H264_7.mp4", new HashMap());
        Bitmap bitmap = mmr.getFrameAtTime();
        saveMyBitmap(bitmap, "01");
    }

    public void saveMyBitmap(Bitmap mBitmap, String bitName) {
        File f = new File("", bitName + ".jpg");
        System.out.print(f.getAbsolutePath());
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

    @Test
    public void testFile() {
        try {
            File downloadFile = new File("D:/G/", "E");
            if (!downloadFile.mkdirs()) {
                downloadFile.createNewFile();
            }
            String path = downloadFile.getAbsolutePath();
            System.out.println(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}