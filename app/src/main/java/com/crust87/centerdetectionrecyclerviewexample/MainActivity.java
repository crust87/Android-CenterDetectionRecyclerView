package com.crust87.centerdetectionrecyclerviewexample;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.crust87.centerdetectionrecyclerview.widget.CenterDetectionRecyclerView;

public class MainActivity extends AppCompatActivity {
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

        loadGUI();
        bindEvent();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void loadGUI() {
        setContentView(R.layout.activity_main);

        mRecyclerView = (CenterDetectionRecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TextAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void bindEvent() {
        mRecyclerView.setOnCenterItemChangeListener(mCenterListener);
    }

    private CenterDetectionRecyclerView.OnCenterItemChangeListener mCenterListener = new CenterDetectionRecyclerView.OnCenterItemChangeListener() {

        TextView lastView;

        @Override
        public void onItemChange(View child, RecyclerView.ViewHolder childHolder) {
            if(lastView != null) {
                lastView.setText("I AM NOT");
            }

            TextAdapter.ClipViewHolder holder = (TextAdapter.ClipViewHolder) childHolder;
            holder.mTextView.setText("I AM CENTER");

            lastView = holder.mTextView;
        }
    };
}
