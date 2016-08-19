package com.meizu.flyme.calendar.subcription_new.recommend;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.banner.Banner;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.banner.BannerItem;
import com.meizu.flyme.calendar.subscription.ImageLoaderUtils;
import com.meizu.flyme.calendar.subscription.ui.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import flyme.support.v7.widget.FadeViewPager;

/**
 * Created by huangzhihao on 16-8-5.
 */
public class BannerView extends FrameLayout {

    static final long AUTO_ROLL_DELAY = 4500L;
    final static int PAGE_SIZE = 5040;

    private FadeViewPager mViewPager;
    //    private PagerIndicator mPagerIndicator;
    private BannerAdapter mAdapter;

    private OnBannerItemClickListener mItemClickListener;

    private Runnable mRollingRunnable = null;
    private boolean mStartRolling = false;

    private boolean mIsTouchToStopRolling = false;

    private int mCurrentPagerPosition = 0;
    private boolean mIsInvisible = false;
    private boolean mIsFirstInit = true;

    private List<BannerItem> mItems = new ArrayList<BannerItem>();

    public interface OnBannerItemClickListener {
        void onItemClick(BannerItem item, int position);
    }

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }

    public void setOnBannerItemClickListener(OnBannerItemClickListener l) {
        mItemClickListener = l;
    }

    public void startFromPosition(int position) {
        if (mAdapter != null && mAdapter.getCount() > 1) {
            mViewPager.setCurrentItem(position, false);
            if (position == 0) {
                mAdapter.onPageSelected(position);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                cancelRollling(true);
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                resume();
                break;

            case MotionEvent.ACTION_CANCEL:
                resume();
                break;
        }
        return false;
    }

    public void start() {
        mStartRolling = true;
        startFromPosition(0);
    }

    public void pause() {
        cancelRollling(false);
    }

    public void resume() {
        if (mRollingRunnable != null) {
            if (mIsInvisible) {
                mViewPager.setCurrentItem(mCurrentPagerPosition);
            }
            mViewPager.requestLayout();
            postDelayed(mRollingRunnable, AUTO_ROLL_DELAY);
        }
    }

    public FadeViewPager getViewPager() {
        return mViewPager;
    }

    public void refreshItems(List<BannerItem> items) {
        if (mAdapter != null) {
            mAdapter.switchItems(items);
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        // 当View不可见时，取消自动轮播
        if (visibility == VISIBLE && mStartRolling) {
            resume();
            mIsInvisible = false;
        } else if (visibility != VISIBLE) {
            cancelRollling(mStartRolling);
            mIsInvisible = true;
        }
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    protected void onDetachedFromWindow() {
        cancelRollling(true);
        super.onDetachedFromWindow();
    }

    public boolean getIsInvisible() {
        return mIsInvisible;
    }

    public void cancelRollling(boolean autoRolling) {
        if (mRollingRunnable != null) {
            removeCallbacks(mRollingRunnable);
            mStartRolling = autoRolling;
        } else {
            mStartRolling = false;
        }
    }

    public void setupItems(List<BannerItem> items) {
        if (mAdapter == null) {
            mAdapter = new BannerAdapter();
        }

        mAdapter.switchItems(items);

        start();
    }


    private void initView(Context context, AttributeSet attrs, int defStyle) {
        inflate(context, R.layout.banner_view, this);
        mViewPager = (FadeViewPager) findViewById(R.id.bannerPager);
//        mViewPager.setFlipMode(FlipMode.FLIP_MODE_OVERLAY);
        mViewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.subscribe_banner_margin));
        mViewPager.setInterpolator(PathInterpolatorCompat.create(0.33f, 0.0f, 0.20f, 1.0f)); // 这个是规范要求的差值器
//        mPagerIndicator = (PagerIndicator) findViewById(R.id.tabs);
        mAdapter = new BannerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(mAdapter);
    }

    private void setCurrentItemDelay(final int position, long mills,
                                     final boolean soomth) {
        if (mRollingRunnable != null) {
            removeCallbacks(mRollingRunnable);
        }

        mRollingRunnable = new Runnable() {
            @Override
            public void run() {
                if (mViewPager != null && mAdapter != null) {
                    int count = mAdapter.getCount();
                    int pos = position % count;

                    if (pos == mViewPager.getCurrentItem()) {
                        pos = (pos + 1) % count;
                    }
                    if (soomth) {
                        mViewPager.setCurrentItem(pos, 500);
                    } else {
                        mViewPager.setCurrentItem(pos, false);
                    }
                }
            }
        };
        postDelayed(mRollingRunnable, mills);
    }

    class BannerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
        Context mContext;

        public BannerAdapter() {
            mContext = getContext();
        }

        public void switchItems(List<BannerItem> items) {
            if (items != null) {
                mItems = items;
            }

            notifyDataSetChanged();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
//            int redirectPosition = position % mItems.size();
//            mPagerIndicator.setCirclePosOffset(positionOffset, redirectPosition);
        }

        @Override
        public void onPageSelected(int position) {
            if (getCount() > 1 && (mIsFirstInit || position != 0)) {
//                int redirectPosition = position % mItems.size();
//                mPagerIndicator.setCirclePosition(redirectPosition);
                mCurrentPagerPosition = position % getCount();
                if (position == 0) {
                    setCurrentItemDelay(getCount() / 2, 250, false);
                } else if (position == getCount() - 1) {
                    setCurrentItemDelay(getCount() / 2 - 1, 250, false);
                } else {
                    setCurrentItemDelay(position + 1, AUTO_ROLL_DELAY, true);
                }
                mIsFirstInit = false;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == 1) {
                mIsTouchToStopRolling = true;
                cancelRollling(true);
            } else if (state == 2) {
                if (mIsTouchToStopRolling) {
                    resume();
                    mIsTouchToStopRolling = false;
                }
            }
        }

        @Override
        public int getCount() {
            int count = mItems.size();
            if (count == 1 || count == 0) {
                return count;
            }

            return PAGE_SIZE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (object instanceof View) {
                View v = (View) object;
                container.removeView(v);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.banner_round_item, null);
            final int innerPosition = position % mItems.size();
            v.setTag(position);

            final BannerItem item = mItems.get(innerPosition);

//            TextView infoView = (TextView) v.findViewById(R.id.text);
//            infoView.setText(item.getName());

            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(item, innerPosition);
                    }
                }
            });

            RoundImageView imageView = (RoundImageView) v.findViewById(R.id.image);
            ImageLoaderUtils.displayImage(item.getImg(), imageView, R.drawable.subscription_large_default, null);

            container.addView(v);
            return v;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            View currentView = (View)object;
            if (currentView == null) {
                return;
            }
            currentView.setId(position);
        }
    }
}

