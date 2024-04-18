package com.lc.template.bugly;

import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;


public class BuglyManager {
    public static void init(Context context, String appId,boolean debug) {
        CrashReport.initCrashReport(context, appId, debug);
    }
}
