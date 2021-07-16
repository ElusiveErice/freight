package com.csu.freightbook.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.csu.freightbook.R;
import com.csu.freightbook.XUIActivity;

public class HomepageActivity extends XUIActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, HomepageActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }
}
