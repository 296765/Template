package com.lc.template.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * Created by Wei Ting
 * on 2023/9/7
 * Description
 */
public class ImagePager extends PagerAdapter {
    private List<Integer> bannerList;

    public ImagePager(List<Integer> bannerList) {
        this.bannerList = bannerList;
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView bannerImageView = new ImageView(container.getContext());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bannerImageView.setLayoutParams(lp);
        bannerImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        bannerImageView.setImageResource(bannerList.get(position));
        container.addView(bannerImageView);
        return bannerImageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
