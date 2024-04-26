package com.lc.template.fragment;

import android.Manifest;
import android.app.Activity;
import android.view.Gravity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.lc.template.R;
import com.lc.template.activity.WebActivity;
import com.lc.template.activity.WelcomeActivity;
import com.lc.template.base.BaseVBFragment;
import com.lc.template.base.CommonAppContext;
import com.lc.template.databinding.FragHomeBinding;
import com.lc.template.dialog.DialogUtils;
import com.lc.template.utils.MToast;
import com.lc.template.utils.Y;
import com.lc.template.utils.picker.OnItemViewClickCallBack;
import com.lc.template.utils.picker.PickerType;
import com.lc.template.utils.picker.PickerViewTool;
import com.lc.template.view.selectPic.HorizontalPicSelector;

/**
 * Created by Wei Ting
 * on 2022/7/13
 * Description：
 */
public class HomeFragment extends BaseVBFragment<FragHomeBinding> {

    @Override
    protected void initView() {


        binding.tvTitle.setOnClickListener(v -> {
                    MToast.show("点击了");
                    update();

//                    ActivityUtils.finishActivity(WelcomeActivity.class);

//                        PickerViewTool.showPickerView(getContext(), null, PickerType.SEX, "asdf", "", new OnItemViewClickCallBack() {
//                            @Override
//                            public void onItemViewClickCallBack(int position, String type, Object object) {
//
//                            }
//                        })
//                        MToast.show("result.message")
//                UpdateVersionActivity.actionStart("bean.getMsg()", true,CommonAppContext.getInstance())


                }

        );

        binding.picSelector.setListener(new HorizontalPicSelector.OnClick() {
            @Override
            public void onAddClick() {
                showPhotoDialog();
            }
        });


    }


    //选择图片
    private void showPhotoDialog() {
        Y.showNotification((Activity) getContext(), Manifest.permission.CAMERA, getResources().getString(R.string.pic_tags));

        DialogUtils.showStringArrayDialog(getContext(), new Integer[]{R.string.alumb, R.string.camera}, new DialogUtils.StringArrayDialogCallback() {
            @Override
            public void onItemClick(String text, int tag) {
                if (tag == R.string.camera) {
                    //相机拍照
                    binding.picSelector.openCamera();
                } else {
                    //选择相册
                    binding.picSelector.openGallery();
                }
            }
        });
    }

}
