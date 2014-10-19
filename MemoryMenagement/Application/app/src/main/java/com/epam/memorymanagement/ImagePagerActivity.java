package com.epam.memorymanagement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class ImagePagerActivity extends FragmentActivity implements OnPageChangeListener {

    private BitmapProvider bitmapProvider;

    private ImagePagerAdapter adapter;
    private ViewPager pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);

        bitmapProvider = BitmapProvider.getInstance();

        adapter = new ImagePagerAdapter(getSupportFragmentManager(), bitmapProvider.getCount());
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(bitmapProvider.getSelectedImageIndex());
        pager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageSelected(int position) {

        bitmapProvider.setSelectedImage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(
            int position, float positionOffset, int positionOffsetPixels) {

    }

    public static class ImagePagerAdapter extends FragmentStatePagerAdapter {

        private final int mSize;

        public ImagePagerAdapter(FragmentManager fm, int size) {

            super(fm);
            mSize = size;
        }

        @Override
        public int getCount() {

            return mSize;
        }

        @Override
        public Fragment getItem(int position) {

            return ImageFragment.newInstance(position);
        }
    }
}
