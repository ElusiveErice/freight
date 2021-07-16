package com.csu.freightbook;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.csu.freightbook.activities.HomepageActivity;
import com.csu.freightbook.activities.SetPatternLockerPasswordActivity;
import com.csu.freightbook.utils.SharedHelper;
import com.github.ihsg.patternlocker.CellBean;
import com.github.ihsg.patternlocker.IHitCellView;
import com.github.ihsg.patternlocker.OnPatternChangeListener;
import com.github.ihsg.patternlocker.PatternLockerView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.xuexiang.xui.widget.button.RippleView;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_PATTERN_LOCKER_PASSWORD_CODE = 0x123;

    private List<Integer> mPatternLockerPassword;
    private PatternLockerView mPatternLockerView;
    private int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        RelativeLayout RLRoot = findViewById(R.id.rl_root);
        RLRoot.setOnClickListener(v -> {
            times++;
            if (times == 20) {
                times = 0;
                initPatternLockerPassword();
            } else {
                if (initPatternLocker()) {
                    makePatternLockerView();
                } else {
                    initPatternLockerPassword();
                }
            }
        });
        initPermission();
    }

    /**
     * 初始化权限请求
     */
    private void initPermission() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (initPatternLocker()) {
                            makePatternLockerView();
                        } else {
                            initPatternLockerPassword();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    /**
     * 从缓存中读取图形密码进行初始化
     *
     * @return 是否存有密码
     */
    private boolean initPatternLocker() {
        SharedHelper sharedHelper = new SharedHelper(this);
        mPatternLockerPassword = sharedHelper.readPatternLockerPassword();
        return !mPatternLockerPassword.isEmpty();
    }

    /**
     * 初始化图形密码，打开设置密码activity，并将结果返回
     */
    private void initPatternLockerPassword() {
        Intent intent = SetPatternLockerPasswordActivity.newIntent(this);
        startActivityForResult(intent, REQUEST_PATTERN_LOCKER_PASSWORD_CODE);
    }

    /**
     * 布置图形密码锁
     */
    private void makePatternLockerView() {
        mPatternLockerView = findViewById(R.id.patternLockerView);
        mPatternLockerView.setVisibility(View.VISIBLE);
        mPatternLockerView.setOnPatternChangedListener(new OnPatternChangeListener() {
            @Override
            public void onStart(@NotNull PatternLockerView patternLockerView) {

            }

            @Override
            public void onChange(@NotNull PatternLockerView patternLockerView, @NotNull List<Integer> list) {

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
                    MainActivity.this.finish();
                }
            }

            @Override
            public void onClear(@NotNull PatternLockerView patternLockerView) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PATTERN_LOCKER_PASSWORD_CODE) {
            if (resultCode == SetPatternLockerPasswordActivity.PATTERN_LOCKER_PASSWORD_RESULT_CODE) {
                String passwordString = data.getStringExtra(SetPatternLockerPasswordActivity.PASSWORD_EXTRA);
                mPatternLockerPassword = new ArrayList<>();
                for (char c : passwordString.toCharArray()) {
                    mPatternLockerPassword.add(c - 48);
                }
                Log.i(TAG, mPatternLockerPassword.toString());
                makePatternLockerView();
            }
        }
    }
}