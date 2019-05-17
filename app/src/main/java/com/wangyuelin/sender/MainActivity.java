package com.wangyuelin.sender;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.wangyuelin.sender.pages.home.HomeFragment;
import com.wangyuelin.sender.pages.home.LeftFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.fl_left)
    FrameLayout flLeft;
    @BindView(R.id.drawer)
    DrawerLayout drawer;

    HomeFragment homeFragment;
    LeftFragment leftFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        homeFragment = HomeFragment.newInstance(null);
        leftFragment = LeftFragment.newInstance(null);
        replace(homeFragment, R.id.fl_content);
        replace(leftFragment, R.id.fl_left);
    }

    /**
     * 将Fragment添加到布局
     * 采取替换的方式，减少一层布局
     * @param fragment
     * @param resId
     */
    private void replace(Fragment fragment, int resId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(resId, fragment);
        transaction.commit();
    }
}
