package com.example.shayanmoradi.xplayer;

import android.os.Bundle;

import com.example.shayanmoradi.xplayer.ViewPagerFragments.AlbumsFragment;
import com.example.shayanmoradi.xplayer.ViewPagerFragments.ArtistsFragment;
import com.example.shayanmoradi.xplayer.ViewPagerFragments.SongsFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ViewPagerAdapter adapter;

private MaterialButton playBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        SongsFragment topAppBarFragment = SongsFragment.newInstance();
        adapter.addFrag(topAppBarFragment,"Top App Bars");

        AlbumsFragment tabLayoutFragment = AlbumsFragment.newInstance();
        adapter.addFrag(tabLayoutFragment,"Tab Layout");

        ArtistsFragment bottomNavigationFragment = ArtistsFragment.newInstance();
        adapter.addFrag(bottomNavigationFragment,"Bottom Navigation");

//        TextFieldsFragment textFieldsFragment = TextFieldsFragment.newInstance();
//        adapter.addFrag(textFieldsFragment,"Text Fields");

        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}
