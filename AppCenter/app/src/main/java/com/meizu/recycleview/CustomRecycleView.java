package com.meizu.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;




/**
 * Created by root on 15-1-4.
 */
public class CustomRecycleView extends RecyclerView{
    public CustomRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    private View mView;

    public interface OnItemScrollChangeListener{
        void onChange(View view, int position);
    }

    private OnItemScrollChangeListener mOnItemScrollChangeListener;

    public void setOnItemScrollChangeListener(OnItemScrollChangeListener onItemScrollChangeListener){
        this.mOnItemScrollChangeListener = onItemScrollChangeListener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mView = getChildAt(0);

        if (mOnItemScrollChangeListener != null)
        {
            mOnItemScrollChangeListener.onChange(mView,
                    getChildPosition(mView));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_MOVE)
        {
            mView = getChildAt(0);

            if (mOnItemScrollChangeListener != null)
            {
                mOnItemScrollChangeListener.onChange(mView,
                        getChildPosition(mView));
            }
        }

        return super.onTouchEvent(e);
    }
}
