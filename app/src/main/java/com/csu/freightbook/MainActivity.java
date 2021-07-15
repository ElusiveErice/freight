package com.csu.freightbook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.csu.freightbook.activities.HomepageActivity;
import com.github.ihsg.patternlocker.CellBean;
import com.github.ihsg.patternlocker.IHitCellView;
import com.github.ihsg.patternlocker.OnPatternChangeListener;
import com.github.ihsg.patternlocker.PatternLockerView;
import com.karumi.dexter.Dexter;
import com.xuexiang.xui.widget.button.RippleView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private List<Integer> mPatternLockerPassword;
    private PatternLockerView mPatternLockerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        //TODO 使用缓存保存图形密码
        mPatternLockerPassword = new ArrayList<>();
        mPatternLockerPassword.add(0);
        mPatternLockerPassword.add(1);
        mPatternLockerPassword.add(4);
        mPatternLockerPassword.add(5);
        mPatternLockerPassword.add(8);
        initPermission();
    }

    /**
     * 初始化权限请求
     */
    private void initPermission() {
//        initPatternLockerView();
        //TODO 申请权限
    }

    private void initPatternLockerView() {
        mPatternLockerView = findViewById(R.id.patternLockerView);
        mPatternLockerView.setOnPatternChangedListener(new OnPatternChangeListener() {
            @Override
            public void onStart(@NotNull PatternLockerView patternLockerView) {
                Log.i(TAG, "start");
            }

            @Override
            public void onChange(@NotNull PatternLockerView patternLockerView, @NotNull List<Integer> list) {
                Log.i(TAG, "change");
                Log.i(TAG, list.toString());
            }

            @Override
            public void onComplete(@NotNull PatternLockerView patternLockerView, @NotNull List<Integer> list) {
                boolean isError = false;
                if (list.size() == mPatternLockerPassword.size()) {
                    for (int i = 0; i < list.size(); i++) {
                        if (!list.get(i).equals(mPatternLockerPassword.get(i))) {
                            isError = true;
                            break;
                        }
                    }
                } else {
                    isError = true;
                }
                mPatternLockerView.updateStatus(isError);
                if (!isError) {
                    Intent intent = HomepageActivity.newIntent(MainActivity.this);
                    startActivity(intent);
                }
            }

            @Override
            public void onClear(@NotNull PatternLockerView patternLockerView) {
                Log.i(TAG, "clear");
            }
        });
    }
}