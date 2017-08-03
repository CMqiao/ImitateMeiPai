package com.yqb.imitatemeipai.app.main.beautyshot;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.base.BaseFragment;

import java.io.IOException;

/**
 * Created by QJZ on 2017/7/30.
 */

public class APlayFragment extends BaseFragment {

    private SurfaceView videoPlayView;
    private ImageView playImage;
    private MediaPlayer player;
    private SurfaceHolder holder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_a_play;
    }

    @Override
    protected void findViews() {
        videoPlayView = (SurfaceView) rootView.findViewById(R.id.sv_video_play);
        playImage = (ImageView) rootView.findViewById(R.id.iv_video_play);
    }

    @Override
    protected void init() {
        player = new MediaPlayer();
        try {
            player.setDataSource(context, Uri.parse("http://mvvideo10.meitudata.com/597cc111dea917818_H264_7.mp4"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder=videoPlayView.getHolder();
        holder.addCallback(new MyCallBack());

        try {
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        videoPlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying()){
                    player.pause();
                    playImage.setVisibility(View.VISIBLE);
                }else{
                    player.start();
                    playImage.setVisibility(View.GONE);
                }
            }
        });

    }

    class MyCallBack implements SurfaceHolder.Callback{

         @Override
         public void surfaceCreated(SurfaceHolder holder) {
            player.setDisplay(holder);
         }

         @Override
         public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

         }

         @Override
         public void surfaceDestroyed(SurfaceHolder holder) {
            player.pause();
         }
     }

    @Override
    public void onPause() {
        super.onPause();
        player.pause();
    }
}
