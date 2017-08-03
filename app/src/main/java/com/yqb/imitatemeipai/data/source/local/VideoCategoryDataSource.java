package com.yqb.imitatemeipai.data.source.local;

import android.content.Context;

import com.yqb.imitatemeipai.R;
import com.yqb.imitatemeipai.entity.common.VideoCategory;

/**
 * Created by QJZ on 2017/8/2.
 */

public class VideoCategoryDataSource {

    private Context context;
    private int[] ids;
    private int[] pictures;
    private String[] titles;

    public VideoCategoryDataSource(Context context) {
        this.context = context;
        ids = context.getResources().getIntArray(R.array.video_category_id);
        pictures = new int[]{R.mipmap.ic_funny, R.mipmap.ic_super_star, R.mipmap.ic_men_of_god, R.mipmap.ic_women_of_god,
                R.mipmap.ic_music, R.mipmap.ic_dance, R.mipmap.ic_nice_food, R.mipmap.ic_beauty_makeup,
                R.mipmap.ic_baby, R.mipmap.ic_adorable_pet, R.mipmap.ic_manual, R.mipmap.ic_wear_show,
                R.mipmap.ic_eat_show};
        titles = context.getResources().getStringArray(R.array.video_category_name);
    }

    public VideoCategory[] generateData() {
        VideoCategory[] videoCategories = new VideoCategory[pictures.length];
        for (int i = 0; i < pictures.length; i++) {
            videoCategories[i] = new VideoCategory(ids[i], pictures[i], titles[i]);
        }
        return videoCategories;
    }

}
