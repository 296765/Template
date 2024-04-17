package com.lc.template.utils.picSelect;

import java.util.HashMap;

public abstract class PermissionCallback {

    public abstract void onAllGranted();

    public void onResult(HashMap<String, Boolean> resultMap) {

    }
}
