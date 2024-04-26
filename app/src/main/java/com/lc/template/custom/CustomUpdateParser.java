/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lc.template.custom;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.lc.template.base.Constants;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.listener.IUpdateParseCallback;
import com.xuexiang.xupdate.proxy.IUpdateParser;


/**
 * 自定义更新解析器
 *
 * @author xuexiang
 * @since 2018/7/12 下午3:46
 */
public class CustomUpdateParser implements IUpdateParser {
    @Override
    public UpdateEntity parseJson(String json) throws Exception {
        return getParseResult(json);
    }

    private UpdateEntity getParseResult(String json) {

        com.lc.template.model.UpdateEntity result = GsonUtils.fromJson(json, com.lc.template.model.UpdateEntity.class);
        if (result != null || result.code== Constants.CODE_SUCCEED) {


            return new UpdateEntity()
                    .setHasUpdate(true)//true 展示提示
                    .setIsIgnorable(true)
                    .setForce(false)
                    .setVersionCode(AppUtils.getAppVersionCode() + 1)
                    .setVersionName("1.0.2")//新版本名
                    .setUpdateContent("有更新")//介绍
                    .setDownloadUrl("http://oss.jiumucm.cn/jiumu.apk")
                    .setSize(20 * 1024);
        }
        return null;

    }

    @Override
    public void parseJson(String json, @NonNull IUpdateParseCallback callback) throws Exception {
        //当isAsyncParser为 true时调用该方法, 所以当isAsyncParser为false可以不实现
        callback.onParseResult(getParseResult(json));
    }


    @Override
    public boolean isAsyncParser() {
        return false;
    }
}
