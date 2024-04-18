package com.lc.template.view.selectPic;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lc.template.R;
import com.lc.template.view.DesignUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Wei Ting
 * on 2022/12/29
 * Description
 */
public class HorizontalPicSelector extends LinearLayout {
    private Context mContext;
    private RecyclerView mRecV;
    private AppCompatImageView iv_add;
    private List<LocalMedia> picMediaList = new ArrayList<>();
    private OnClick listener;
    private boolean enabled = true;

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.enabled = enabled;
    }

    public void setListener(OnClick listener) {
        this.listener = listener;
    }

    public HorizontalPicSelector(@NonNull Context context) {
        super(context);
        init(context);
    }

    public HorizontalPicSelector(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalPicSelector(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mContext = context;
        View.inflate(context, R.layout.layout_pic_select, this);
        mRecV = findViewById(R.id.rv_list);
        iv_add = findViewById(R.id.iv_add);

        mRecV.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        mRecV.addItemDecoration(new LinearItemDecoration(context, HORIZONTAL, DesignUtils.dp2px(context, 10), getResources().getColor(R.color.color_f6)));
        mRecV.setAdapter(new PicAdapter());
        iv_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开相册
//                openGallery();
                if (listener != null) {
                    listener.onAddClick();
                } else {
                    Log.e("picSelector", "listener null");
                }
            }
        });
        iv_add.setVisibility(enabled? VISIBLE: GONE);
    }

    public void openGallery() {
        PictureSelector.create((Activity) mContext)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(3)
                .imageSpanCount(3)
                .isCamera(false)
                .selectionData(picMediaList)
                .isCompress(true)
                .minimumCompressSize(100)
                .compressQuality(80)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        picMediaList.clear();
                        picMediaList.addAll(result);
                        mRecV.setVisibility(picMediaList.size() == 0? GONE: VISIBLE);
                        iv_add.setVisibility(picMediaList.size() >= 3? GONE: VISIBLE);
                        Objects.requireNonNull(mRecV.getAdapter()).notifyDataSetChanged();
                    }

                    @Override
                    public void onCancel() {
                        // onCancel Callback
                    }
                });
    }

    public void openCamera() {
        PictureSelector.create((Activity) mContext)
                .openCamera(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .isCompress(true)
                .minimumCompressSize(100)
                .compressQuality(80)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        picMediaList.addAll(result);
                        mRecV.setVisibility(picMediaList.size() == 0? GONE: VISIBLE);
                        iv_add.setVisibility(picMediaList.size() >= 3? GONE: VISIBLE);
                        Objects.requireNonNull(mRecV.getAdapter()).notifyDataSetChanged();
                    }

                    @Override
                    public void onCancel() {
                        // onCancel Callback
                    }
                });
    }

    public void show(int position) {
        PictureSelector.create((Activity) mContext)
                .themeStyle(R.style.picture_default_style)
                .isNotPreviewDownload(true)
                .imageEngine(GlideEngine.createGlideEngine())
                .openExternalPreview(position, picMediaList);
    }

    public List<LocalMedia> getPicList() {
        return picMediaList;
    }

    public interface OnClick {
        void onAddClick();
    }

    private class PicAdapter extends RecyclerView.Adapter<PicAdapter.PicViewHolder> {

        @NonNull
        @Override
        public PicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_pics, parent, false);
            return new PicViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PicViewHolder holder,  int position) {
            RoundedCorners roundedCorners = new RoundedCorners(45);//圆角为5
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
            Glide.with(mContext)
                    .load(picMediaList.get(position).getPath())
                    .apply(options)
                    .into(holder.iv_pic);
            holder.iv_pic.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //预览
                    show(position);
                }
            });
            holder.iv_del.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    picMediaList.remove(position);
                    mRecV.setVisibility(picMediaList.size() == 0? GONE: VISIBLE);
                    iv_add.setVisibility(picMediaList.size() >= 3? GONE: VISIBLE);
                    notifyDataSetChanged();
                }
            });
            holder.iv_del.setVisibility(enabled? VISIBLE: GONE);
        }

        @Override
        public int getItemCount() {
            return picMediaList == null? 0: picMediaList.size();
        }

        private class PicViewHolder extends RecyclerView.ViewHolder {
            ImageView iv_pic;
            ImageView iv_del;
            public PicViewHolder(@NonNull View itemView) {
                super(itemView);
                iv_pic = itemView.findViewById(R.id.iv_pic);
                iv_del = itemView.findViewById(R.id.iv_del);
            }
        }
    }

    public void setData (List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            LocalMedia lm = new LocalMedia();
            lm.setPath(data.get(i));
            picMediaList.add(lm);
        }
        mRecV.setVisibility(picMediaList.size() == 0? GONE: VISIBLE);
        iv_add.setVisibility(picMediaList.size() >= 3? GONE: VISIBLE);
        Objects.requireNonNull(mRecV.getAdapter()).notifyDataSetChanged();
    }
}
