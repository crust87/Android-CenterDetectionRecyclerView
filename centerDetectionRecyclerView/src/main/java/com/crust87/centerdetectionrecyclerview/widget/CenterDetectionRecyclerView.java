package com.crust87.centerdetectionrecyclerview.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mabi on 2016. 1. 15..
 */
public class CenterDetectionRecyclerView extends RecyclerView {

    private OnCenterItemChangeListener mOnCenterItemChangeListener;

    public CenterDetectionRecyclerView(Context context) {
        super(context);

        initView();
    }

    public CenterDetectionRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public CenterDetectionRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView();
    }

    private void initView() {
        addOnScrollListener(mListViewScrollListener);
    }

    private RecyclerView.OnScrollListener mListViewScrollListener = new RecyclerView.OnScrollListener() {

        private View postView;
        private ViewHolder postHolder;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if(mOnCenterItemChangeListener == null) {
                return;
            }

            int center = recyclerView.getHeight() / 2;

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                for(int i = 0; i < recyclerView.getChildCount(); i++) {
                    View currentView = recyclerView.getChildAt(i);
                    float top = currentView.getY();
                    float bottom =  currentView.getY() + currentView.getHeight();

                    if(center > top && center < bottom) {
                        ViewHolder currentHolder = recyclerView.getChildViewHolder(currentView);

                        if(postHolder != currentHolder) {
                            postView = currentView;
                            postHolder = currentHolder;

                            mOnCenterItemChangeListener.onItemChange(currentView, currentHolder);
                            return;
                        }
                    }
                }
            }
        }
    };

    public void setOnCenterItemChangeListener(OnCenterItemChangeListener onCenterItemChangeListener) {
        mOnCenterItemChangeListener = onCenterItemChangeListener;
    }

    public interface OnCenterItemChangeListener {
        void onItemChange(View child, RecyclerView.ViewHolder holder);
    }
}
