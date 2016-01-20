/*
 * Android-CenterDetectionRecyclerView
 * https://github.com/crust87/Android-FFmpegExecutor
 *
 * Mabi
 * crust87@gmail.com
 * last modify 2016-01-20
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    private CenterDetectionRecyclerListener mCenterDetectionRecyclerListener;
    private RecyclerListener mRecyclerListener;
    private ViewHolder workingHolder;

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
        super.addOnScrollListener(mListViewScrollListener);

        super.setRecyclerListener(new RecyclerListener() {
            @Override
            public void onViewRecycled(ViewHolder holder) {
                if (mRecyclerListener != null) {
                    mRecyclerListener.onViewRecycled(holder);
                }

                if (workingHolder == holder) {
                    workingHolder = null;
                    if (mCenterDetectionRecyclerListener != null) {
                        mCenterDetectionRecyclerListener.onCenterViewRecycled(CenterDetectionRecyclerView.this, holder);
                    }
                }
            }
        });
    }

    public ViewHolder getCenterViewHolder() {
        int center = getHeight() / 2;

        for (int i = 0; i < getChildCount(); i++) {
            View currentView = getChildAt(i);
            float top = currentView.getY();
            float bottom = currentView.getY() + currentView.getHeight();

            if (center > top && center < bottom) {
                ViewHolder currentHolder = getChildViewHolder(currentView);
                workingHolder = currentHolder;

                return workingHolder;
            }
        }

        workingHolder = null;
        return workingHolder;
    }

    private RecyclerView.OnScrollListener mListViewScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (mCenterDetectionRecyclerListener == null) {
                return;
            }

            int center = getHeight() / 2;

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                for (int i = 0; i < getChildCount(); i++) {
                    View currentView = getChildAt(i);
                    float top = currentView.getY();
                    float bottom = currentView.getY() + currentView.getHeight();

                    if (center > top && center < bottom) {
                        ViewHolder currentHolder = getChildViewHolder(currentView);

                        if (workingHolder != currentHolder) {
                            mCenterDetectionRecyclerListener.onCenterItemChange(CenterDetectionRecyclerView.this, currentHolder, workingHolder);
                            workingHolder = currentHolder;

                            return;
                        }
                    }
                }
            }
        }
    };

    @Override
    public void setRecyclerListener(RecyclerListener listener) {
        mRecyclerListener = listener;
    }

    public void setCenterDetectionRecyclerListener(CenterDetectionRecyclerListener listener) {
        mCenterDetectionRecyclerListener = listener;
    }

    public interface CenterDetectionRecyclerListener {
        void onCenterViewRecycled(RecyclerView recyclerView, ViewHolder viewHolder);
        void onCenterItemChange(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder oldViewHolder);
    }
}
