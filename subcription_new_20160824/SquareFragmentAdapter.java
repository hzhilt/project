package com.meizu.flyme.calendar.subcription_new;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.meizu.flyme.calendar.subcription_new.classify.ClassifyFragment;
import com.meizu.flyme.calendar.subcription_new.recommend.RecommendFragment;

import java.lang.ref.WeakReference;

/**
 * Created by huangzhihao on 16-8-4.
 */
public class SquareFragmentAdapter extends FragmentPagerAdapter {

    private String[] mNames;

    private final SparseArray<WeakReference<Fragment>> mFragments = new SparseArray<>();

    public SquareFragmentAdapter(FragmentManager fm, String[] names) {
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

        final String tool = mNames[position];
        if (tool.equals(mNames[0])) {
            return new RecommendFragment();
        } else if (tool.equals(mNames[1])) {
            return new ClassifyFragment();
        }
        return null;
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
