package com.lc.template.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.lc.template.MainActivity;
import com.lc.template.R;
import com.lc.template.adapter.ImagePager;
import com.lc.template.base.BaseVBActivity;
import com.lc.template.base.CommonAppConfig;
import com.lc.template.databinding.ActGuideBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wei Ting
 * on 2024/4/19
 * Description
 */
public class GuideActivity extends BaseVBActivity<ActGuideBinding> {
    private boolean isLastPage = false;
    private boolean isDragPage = false;
    private boolean canJumpPage = true;
    private List<Integer> list = new ArrayList<>();

    @Override
    protected void initView() {
        list.add(R.mipmap.guide1);
        list.add(R.mipmap.guide2);
        list.add(R.mipmap.guide3);
        initAdapter();
    }

    private void initAdapter() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) binding.viewPager.getLayoutParams();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
        binding.viewPager.setLayoutParams(lp);
        binding.viewPager.setAdapter(new ImagePager(list));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (isLastPage && isDragPage && positionOffsetPixels == 0){   //当前页是最后一页，并且是拖动状态，并且像素偏移量为0
                    if (canJumpPage){
                        CommonAppConfig.getInstance().setGuide("1");
                        MainActivity.actionStart(mContext);

                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                isLastPage = position == list.size()-1;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                isDragPage = state == 1;
            }
        });
    }
    /**
     * 用于其他Activty跳转到该Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
