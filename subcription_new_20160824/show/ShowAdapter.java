package com.meizu.flyme.calendar.subcription_new.show;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

/**
 * Created by huangzhihao on 16-8-16.
 */
public class ShowAdapter extends FragmentPagerAdapter {

    private String[] mNames;

    private final SparseArray<WeakReference<Fragment>> mFragments = new SparseArray<>();

    public ShowAdapter(FragmentManager fm,String[] names) {
        super(fm);
        mNames = names;
    }

    @Override
    public Fragment getItem(int position) {
        final WeakReference<Fragment> weakFragment = mFragments
                .get(position);
        if (weakFragment != null && weakFragment.get() != null) {
            return weakFragment.get();
        }

        String cate = mNames[position];
        return BaseShowFragment.newInstance(cate, 0);

    }

    @Override
    public int getCount() {
        return mNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mNames[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        WeakReference<Fragment> weakFragment = mFragments.get(position);
        if (weakFragment != null && weakFragment.get() != null) {
            return weakFragment.get();
        }

        final Fragment fragment = (Fragment) super.instantiateItem(
                container, position);
        mFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        final WeakReference<Fragment> weekFragment = mFragments
                .get(position);
        if (weekFragment != null) {
            weekFragment.clear();
        }
    }
}
