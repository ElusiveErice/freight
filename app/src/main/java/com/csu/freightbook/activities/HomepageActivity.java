package com.csu.freightbook.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.csu.freightbook.R;
import com.csu.freightbook.XUIActivity;
import com.csu.freightbook.fragments.CheckBookFragment;
import com.csu.freightbook.fragments.PooledAnalysisFragment;
import com.csu.freightbook.fragments.WriteBillFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xuexiang.xui.widget.actionbar.TitleBar;

public class HomepageActivity extends XUIActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, HomepageActivity.class);
        return intent;
    }

    private BottomNavigationView mBottomMenu;

    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        mBottomMenu = findViewById(R.id.bottom_menu);

        mFragmentManager = getSupportFragmentManager();
        mFragment = mFragmentManager.findFragmentById(R.id.fragment_content);
        if (mFragment == null) {
            mFragment = WriteBillFragment.newInstance();
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_content, mFragment)
                    .commit();
        }

        mBottomMenu.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.item_write_bill) {
                mFragment = WriteBillFragment.newInstance();
            } else if (menuItem.getItemId() == R.id.item_check_book) {
                mFragment = CheckBookFragment.newInstance();
            } else if (menuItem.getItemId() == R.id.item_pooled_analysis) {
                mFragment = PooledAnalysisFragment.newInstance();
            }

            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_content, mFragment)
                    .commit();

            return true;
        });
    }
}
