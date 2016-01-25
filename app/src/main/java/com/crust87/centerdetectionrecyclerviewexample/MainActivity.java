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

package com.crust87.centerdetectionrecyclerviewexample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.crust87.centerdetectionrecyclerview.widget.CenterDetectionRecyclerView;

public class MainActivity extends AppCompatActivity {

    public static int BACKGROUND_COLOR = Color.parseColor("#7e7e7e");

    protected Context mContext;
    protected Handler mHandler;

    // Layout Components
    private CenterDetectionRecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();
        mHandler = new Handler();
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new TextAdapter();
        loadGUI();
        bindEvent();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextAdapter.TextViewHolder currentHolder = (TextAdapter.TextViewHolder) mRecyclerView.getCenterViewHolder();

                if(currentHolder != null) {
                    currentHolder.mLayout.setBackgroundColor(Color.BLACK);
                    currentHolder.mTextView.setText("I AM CENTER");
                }
            }
        }, 1000);
    }

    private void loadGUI() {
        setContentView(R.layout.activity_main);

        mRecyclerView = (CenterDetectionRecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void bindEvent() {
        mRecyclerView.setCenterDetectionRecyclerListener(mRecyclerListener);
    }

    private CenterDetectionRecyclerView.CenterDetectionRecyclerListener mRecyclerListener = new CenterDetectionRecyclerView.CenterDetectionRecyclerListener() {

        @Override
        public void onCenterItemOut(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            TextAdapter.TextViewHolder holder = (TextAdapter.TextViewHolder) viewHolder;
            holder.mLayout.setBackgroundColor(BACKGROUND_COLOR);
            holder.mTextView.setText("I AM NOT");
        }

        @Override
        public void onCenterItemIn(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            TextAdapter.TextViewHolder holder = (TextAdapter.TextViewHolder) viewHolder;
            holder.mLayout.setBackgroundColor(Color.BLACK);
            holder.mTextView.setText("I AM CENTER");
        }
    };

    public class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextViewHolder> {

        public class TextViewHolder extends RecyclerView.ViewHolder {
            public FrameLayout mLayout;
            public TextView mTextView;

            public TextViewHolder(View view, FrameLayout layout, TextView textView) {
                super(view);
                mLayout = layout;
                mTextView = textView;
            }
        }

        @Override
        public TextAdapter.TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);

            FrameLayout layout = (FrameLayout) view.findViewById(R.id.layout);
            TextView textView = (TextView) view.findViewById(R.id.text);

            return new TextViewHolder(view, layout, textView);
        }

        @Override
        public void onBindViewHolder(TextViewHolder holder, int position) {
            holder.mLayout.setBackgroundColor(BACKGROUND_COLOR);
            holder.mTextView.setText("I AM NOT");
        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }
}
