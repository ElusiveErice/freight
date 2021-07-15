package com.csu.freightbook;

import android.app.Application;

import com.xuexiang.xui.XUI;

public class MainApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this);
        XUI.debug(true);
    }
}
