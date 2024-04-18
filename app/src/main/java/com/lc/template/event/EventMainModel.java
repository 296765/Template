package com.lc.template.event;

import com.kunminx.architecture.ui.callback.UnPeekLiveData;

/**
 * Created by Wei Ting
 * on 2023/3/6
 * Description
 */
public class EventMainModel {

    private static EventMainModel instance;

    public static EventMainModel getInstance() {
        if (instance == null) {
            instance = new EventMainModel();
        }
        return instance;
    }

    public UnPeekLiveData<String> RefreshList = new UnPeekLiveData<>();

}
