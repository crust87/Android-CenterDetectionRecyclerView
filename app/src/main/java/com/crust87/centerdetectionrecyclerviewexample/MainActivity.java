package com.crust87.centerdetectionrecyclerviewexample;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.crust87.centerdetectionrecyclerview.widget.CenterDetectionRecyclerView;
import com.crust87.centerdetectionrecyclerviewexample.widget.SquareCenterCropImageView;
import com.crust87.centerdetectionrecyclerviewexample.widget.SquareCenterCropVideoView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    protected Context mContext;
    protected Handler mHandler;

    // List Components
    private ArrayList<Clip> mClipArrayList;

    // Layout Components
    private CenterDetectionRecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Layout working variable
    private SquareCenterCropVideoView mCurrentVideoView;
    private SquareCenterCropImageView mCurrentImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();
        mHandler = new Handler();

        // Construct Components
        mClipArrayList = new ArrayList<>();

        loadGUI();
        bindEvent();
        init();
    }

    @Override
    public void onPause() {
        if (mCurrentVideoView != null) {
            mCurrentVideoView.stopPlayback();
        }

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        mCurrentVideoView = null;
        mCurrentImageView = null;

        super.onDestroy();
    }

    private void loadGUI() {
        setContentView(R.layout.activity_main);

        mRecyclerView = (CenterDetectionRecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ClipListAdapter(this, mClipArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void bindEvent() {
//        mRecyclerView.addOnScrollListener(mListViewScrollListener);
        mRecyclerView.setOnCenterItemChangeListener(mCenterListener);
    }

    private void init() {
        // Initialize working variables;
        mCurrentVideoView = null;
        mCurrentImageView = null;

        mClipArrayList.add(new Clip(1, "https://kayadami.blob.core.windows.net/userfilesbucket/user_1517735097077165/1517735097077165_1452240785_v.mp4", "https://kayadami.blob.core.windows.net/userfilesbucket/user_1517735097077165/1517735097077165_1452240785_t.jpg"));
        mClipArrayList.add(new Clip(2, "https://kayadami.blob.core.windows.net/userfilesbucket/user_1517885729247960/1517885729247960_1452346368_v.mp4", "https://kayadami.blob.core.windows.net/userfilesbucket/user_1517885729247960/1517885729247960_1452346368_t.jpg"));
        mClipArrayList.add(new Clip(3, "http://1.255.56.21/media/clip/34/34_197_1bf25bc6fa99c1382d4ad0078d1e2a4b19937e38.mp4", "http://1.255.56.21/media/clip/34/34_197_1bf25bc6fa99c1382d4ad0078d1e2a4b19937e38.jpg"));
        mClipArrayList.add(new Clip(4, "https://kayadami.blob.core.windows.net/userfilesbucket/user_1517735097077165/1517735097077165_1452240785_v.mp4", "https://kayadami.blob.core.windows.net/userfilesbucket/user_1517735097077165/1517735097077165_1452240785_t.jpg"));
        mClipArrayList.add(new Clip(5, "https://kayadami.blob.core.windows.net/userfilesbucket/user_1517885729247960/1517885729247960_1452346368_v.mp4", "https://kayadami.blob.core.windows.net/userfilesbucket/user_1517885729247960/1517885729247960_1452346368_t.jpg"));
        mClipArrayList.add(new Clip(6, "http://1.255.56.21/media/clip/34/34_197_1bf25bc6fa99c1382d4ad0078d1e2a4b19937e38.mp4", "http://1.255.56.21/media/clip/34/34_197_1bf25bc6fa99c1382d4ad0078d1e2a4b19937e38.jpg"));
    }

    private CenterDetectionRecyclerView.OnCenterItemChangeListener mCenterListener = new CenterDetectionRecyclerView.OnCenterItemChangeListener() {

        @Override
        public void onItemChange(View child, RecyclerView.ViewHolder childHolder) {
            stopClip();

            Clip lCurrentClip = null;
            try {
                lCurrentClip = (Clip) child.getTag();
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            if (lCurrentClip != null && child != null) {
                ClipListAdapter.ClipViewHolder holder = (ClipListAdapter.ClipViewHolder) childHolder;

                mCurrentVideoView = holder.mVideoView;
                mCurrentImageView = holder.mImageView;

                if (mCurrentVideoView != null) {
                    new LoadVideoTask(mCurrentVideoView, lCurrentClip.video, mContext).execute();
                    mCurrentVideoView.start();
                }

                if (mCurrentImageView != null) {
                    mCurrentImageView.postDelayed(imageRunnable, 100);
                }
            }
        }

        private Runnable imageRunnable = new Runnable() {
            @Override
            public void run() {
                if (mCurrentImageView != null && mCurrentVideoView != null) {
                    if (mCurrentVideoView.isPlaying() && mCurrentVideoView.getCurrentPosition() > 1) {
                        mCurrentImageView.setVisibility(View.GONE);
                    } else {
                        mCurrentImageView.postDelayed(this, 100);
                    }
                }
            }
        };

        private void stopClip() {
            if (mCurrentVideoView != null) {
                mCurrentVideoView.pause();
            }

            mCurrentVideoView = null;
            mCurrentImageView = null;
        }
    };
}
