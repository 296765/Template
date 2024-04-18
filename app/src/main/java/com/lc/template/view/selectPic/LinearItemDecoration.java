package com.lc.template.view.selectPic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

/**
 * LinearLayoutManager 分割线
 *
 * @author JokerCats on 2020.08.10
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private Drawable mDivider;
    private int mDividerSize = 2;
    private int mOrientation;
    private boolean mIncludeEdge = true;
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param orientation 列表方向
     */
    public LinearItemDecoration(Context context, int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;

        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    /**
     * 自定义分割线
     *
     * @param orientation 列表方向
     * @param resId       分割线图片
     */
    public LinearItemDecoration(Context context, int orientation, @DrawableRes int resId) {
        this(context, orientation);
        mDivider = ContextCompat.getDrawable(context, resId);
        if (mDivider != null) {
            mDividerSize = mDivider.getIntrinsicHeight();
        }
    }

    /**
     * 自定义分割线
     *
     * @param orientation  列表方向
     * @param dividerSize  分割线 高度
     * @param dividerColor 分割线 颜色
     */
    public LinearItemDecoration(Context context, int orientation, int dividerSize, int dividerColor) {
        this(context, orientation);
        mDividerSize = dividerSize;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 隐藏 ItemDecoration 边界
     */
    public LinearItemDecoration shieldEdge() {
        mIncludeEdge = false;
        return this;
    }

    /**
     * 获取分割线尺寸
     */
    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view,
                               @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }

        int lastPosition = state.getItemCount() - 1;
        int position = parent.getChildAdapterPosition(view);

        if (mOrientation == LinearLayoutManager.VERTICAL) {
            if (mIncludeEdge || position < lastPosition) {
                outRect.set(0, 0, 0, mDividerSize);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        } else {
            if (mIncludeEdge || position < lastPosition) {
                outRect.set(0, 0, mDividerSize, 0);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        }
    }

    /**
     * 绘制分割线
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    /**
     * 绘制纵向列表时的分隔线  这时分隔线是横着的
     * l、r 不变； t、b 变化
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerSize;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }

            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    /**
     * 绘制横向列表时的分隔线  这时分隔线是竖着的
     * l、r 变化； t、b 不变
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerSize;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }
}
