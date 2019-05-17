package com.wangyuelin.sender.pages.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.wangyuelin.sender.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    String[] titles = new String[]{"待配送", "正在配送", "已配送"};

    List<Fragment> fragments = null;
    ViewPagerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments = new ArrayList<>();
        fragments.add(WaitSendFragment.newInstance(null));
        fragments.add(WaitSendFragment.newInstance(null));
        fragments.add(WaitSendFragment.newInstance(null));

        adapter = new ViewPagerAdapter(getChildFragmentManager() ,fragments, titles);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    private void initView() {
        //初始化Tablayout
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        for (String title : titles) {
            tablayout.addTab(tablayout.newTab().setText(title));
        }

        //初始化ViewPager
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);


    }


    private static class ViewPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragments;
        private String[] titles;

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    /**
     * 实例化
     * @param bundle
     * @return
     */
    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }
}
