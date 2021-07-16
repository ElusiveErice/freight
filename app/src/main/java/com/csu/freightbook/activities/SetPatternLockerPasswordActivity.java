package com.csu.freightbook.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.csu.freightbook.R;
import com.csu.freightbook.XUIActivity;
import com.csu.freightbook.utils.SharedHelper;
import com.github.ihsg.patternlocker.OnPatternChangeListener;
import com.github.ihsg.patternlocker.PatternIndicatorView;
import com.github.ihsg.patternlocker.PatternLockerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SetPatternLockerPasswordActivity extends XUIActivity {

    private static final String TAG = "SetActivity";

    public static final int PATTERN_LOCKER_PASSWORD_RESULT_CODE = 1;

    public static final String PASSWORD_EXTRA = "password_extra";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SetPatternLockerPasswordActivity.class);
        return intent;
    }


    private PatternLockerView mPatternLockerView;
    private PatternIndicatorView mPatternIndicatorView;
    private TextView mTVNotice;

    private List<Integer> mPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pattern_locker_password);

        mPatternLockerView = findViewById(R.id.patternLockerView);
        mPatternIndicatorView = findViewById(R.id.pattern_indicator_view);
        mTVNotice = findViewById(R.id.tv_notice);

        mTVNotice.setText("设置解锁图案");
        mPatternLockerView.setOnPatternChangedListener(new OnPatternChangeListener() {
            @Override
            public void onStart(@NotNull PatternLockerView patternLockerView) {

            }

            @Override
            public void onChange(@NotNull PatternLockerView patternLockerView, @NotNull List<Integer> list) {
                mPatternIndicatorView.updateState(list, false);
            }

            @Override
            public void onComplete(@NotNull PatternLockerView patternLockerView, @NotNull List<Integer> list) {
                if (list.size() <= 3) {
                    mPatternLockerView.updateStatus(true);
                    mPatternIndicatorView.updateState(list, true);
                    mTVNotice.setText("至少连接4个点，请重新绘制");
                    mTVNotice.setTextColor(getColor(R.color.red));
                    return;
                }
                if (mPassword == null) {
                    mPassword = new ArrayList<>();
                    mPassword.addAll(list);
                    mTVNotice.setText("请再次绘制解锁图案");
                    mTVNotice.setTextColor(getColor(R.color.colorPrimaryDark));
                } else if (mPassword.size() == list.size()) {
                    for (int i = 0; i < mPassword.size(); i++) {
                        if (!mPassword.get(i).equals(list.get(i))) {
                            mPatternLockerView.updateStatus(true);
                            mPatternIndicatorView.updateState(list, true);
                            mTVNotice.setText("与上次绘制不一致，请重新绘制");
                            mTVNotice.setTextColor(getColor(R.color.red));
                            mPassword = null;
                            return;
                        }
                    }
                    mTVNotice.setText("手势解锁图案设置成功!");
                    mTVNotice.setTextColor(getColor(R.color.colorPrimaryDark));
                    finishActivity();
                } else {
                    mPatternLockerView.updateStatus(true);
                    mPatternIndicatorView.updateState(list, true);
                    mTVNotice.setText("与上次绘制不一致，请重新绘制");
                    mTVNotice.setTextColor(getColor(R.color.red));
                    mPassword = null;
                }
            }

            @Override
            public void onClear(@NotNull PatternLockerView patternLockerView) {

            }
        });
    }

    /**
     * 完成密码设置后的工作，存入缓存并返回给主页
     */
    private void finishActivity() {
        SharedHelper sharedHelper = new SharedHelper(this);
        sharedHelper.savePatternLockerPassword(mPassword);
        Intent intent = new Intent();
        StringBuilder passwordBuilder = new StringBuilder();
        for (Integer integer : mPassword) {
            passwordBuilder.append(integer);
        }
        intent.putExtra(PASSWORD_EXTRA, passwordBuilder.toString());
        setResult(PATTERN_LOCKER_PASSWORD_RESULT_CODE, intent);
        this.finish();
    }

}
