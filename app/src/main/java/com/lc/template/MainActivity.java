package com.lc.template;
import androidx.fragment.app.Fragment;
import android.os.SystemClock;
import android.view.KeyEvent;
import com.lc.template.base.BaseVBActivity;
import com.lc.template.custom.VpAdapter;
import com.lc.template.databinding.ActivityMainBinding;
import com.lc.template.fragment.HomeFragment;
import com.lc.template.fragment.MessageFragment;
import com.lc.template.fragment.MineFragment;
import com.lc.template.utils.Y;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseVBActivity<ActivityMainBinding> {
   private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;
    @Override
    protected void initView() {
        select(0);
        initVpg();
    }

    private void initVpg() {
        List<Fragment> fragments = new ArrayList<>();
        homeFragment = new HomeFragment();
        messageFragment = new MessageFragment();
        mineFragment = new MineFragment();
        fragments.add(homeFragment);
        fragments.add(messageFragment);
        fragments.add(mineFragment);

        VpAdapter adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        binding.vpgContent.setOffscreenPageLimit(3);
        binding.vpgContent.setAdapter(adapter);
        binding.vpgContent.setCanScroll(false);

        binding.btTabHome.setOnClickListener(view -> select(0) );
        binding.btTabMessage.setOnClickListener(view ->  select(1) );
        binding.btTabMine.setOnClickListener(view ->  select(2) );
    }

    private void select(int i) {
        binding.vpgContent.setCurrentItem(i);

        binding.ivTabHome.setColorFilter(Y.getColor(R.color.color_6));
        binding.ivTabMessage.setColorFilter(Y.getColor(R.color.color_6));
        binding.ivTabMine.setColorFilter(Y.getColor(R.color.color_6));
        binding.tvTabHome.setTextColor(Y.getColor(R.color.color_6));
        binding.tvTabMessage.setTextColor(Y.getColor(R.color.color_6));
        binding.tvTabMine.setTextColor(Y.getColor(R.color.color_6));

        switch (i) {
            case 0:
                binding.ivTabHome.setColorFilter(Y.getColor(R.color.color_main));
                binding.tvTabHome.setTextColor(Y.getColor(R.color.color_main));
                break;
            case 1:
                binding.ivTabMessage.setColorFilter(Y.getColor(R.color.color_main));
                binding.tvTabMessage.setTextColor(Y.getColor(R.color.color_main));
                break;
            case 2:
                binding.ivTabMine.setColorFilter(Y.getColor(R.color.color_main));
                binding.tvTabMine.setTextColor(Y.getColor(R.color.color_main));
                break;

        }
    }

    private boolean isExit;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                Y.t("再按一次返回键退出");
                isExit = true;
                new Thread() {
                    public void run() {
                        SystemClock.sleep(2000);
                        isExit = false;
                    }

                }.start();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}