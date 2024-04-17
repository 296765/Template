package com.lc.template.view;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * Created by Wei Ting
 * on 2022/7/13
 * Description：
 */
public class SmartRefreshLayoutC extends SmartRefreshLayout {
    public SmartRefreshLayoutC(Context context) {
        super(context);
    }

    public SmartRefreshLayoutC(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRefreshHeader(new ClassicsHeader(context), 0, DesignUtils.dp2px(context, 45));
        setRefreshFooter(new ClassicsFooter(context), 0, DesignUtils.dp2px(context, 45));
    }

}
