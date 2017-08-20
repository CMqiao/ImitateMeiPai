package com.yqb.imitatemeipai.data.source.local;

import android.content.Context;

import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.entity.common.PlayVideo;

/**
 * Created by QJZ on 2017/8/18.
 */

public class APlayDataSource {

    private String[] urls;
    private int[] covers;
    private String[] avatars;
    private String[] nickNames;

    public APlayDataSource(Context context) {
        urls = context.getResources().getStringArray(R.array.a_play_video_list_urls);
        avatars = context.getResources().getStringArray(R.array.a_play_video_list_avatars);
        nickNames = context.getResources().getStringArray(R.array.a_play_video_list_nick_names);
        covers = new int[]{R.drawable.ic_video_cover00, R.drawable.ic_video_cover01, R.drawable.ic_video_cover02,
                R.drawable.ic_video_cover03, R.drawable.ic_video_cover04, R.drawable.ic_video_cover05, R.drawable.ic_video_cover06};
    }

    public PlayVideo[] generateData() {
        PlayVideo[] playVideos = new PlayVideo[urls.length];
        for (int i = 0; i < urls.length; i++) {
            playVideos[i] = new PlayVideo(avatars[i], covers[i], nickNames[i], urls[i]);
        }
        return playVideos;
    }

}
