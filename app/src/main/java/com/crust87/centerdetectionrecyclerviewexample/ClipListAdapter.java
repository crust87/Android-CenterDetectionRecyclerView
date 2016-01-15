package com.crust87.centerdetectionrecyclerviewexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crust87.centerdetectionrecyclerviewexample.widget.SquareCenterCropImageView;
import com.crust87.centerdetectionrecyclerviewexample.widget.SquareCenterCropVideoView;

import java.util.ArrayList;

/**
 * Created by mabi on 2016. 1. 15..
 */
public class ClipListAdapter extends RecyclerView.Adapter<ClipListAdapter.ClipViewHolder> {

    private Context mContext;
    private ArrayList<Clip> mClipArrayList;

    public static class ClipViewHolder extends RecyclerView.ViewHolder {
        public SquareCenterCropVideoView mVideoView;
        public SquareCenterCropImageView mImageView;

        public ClipViewHolder(View view, SquareCenterCropVideoView videoView, SquareCenterCropImageView imageView) {
            super(view);
            mVideoView = videoView;
            mImageView = imageView;
        }
    }

    public ClipListAdapter(Context context, ArrayList<Clip> clipArrayList) {
        mContext = context;
        mClipArrayList = clipArrayList;
    }

    @Override
    public ClipListAdapter.ClipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_clip, parent, false);


        SquareCenterCropVideoView videoView = (SquareCenterCropVideoView) view.findViewById(R.id.videoClip);
        SquareCenterCropImageView imageView = (SquareCenterCropImageView) view.findViewById(R.id.imageClip);

        return new ClipViewHolder(view, videoView, imageView);
    }

    @Override
    public void onBindViewHolder(ClipViewHolder holder, int position) {
        Clip data = mClipArrayList.get(position);

        holder.itemView.setTag(data);
        holder.mVideoView.pause();
        holder.mVideoView.stopPlayback();

        holder.mImageView.setImageBitmap(null);
        holder.mImageView.setVisibility(View.VISIBLE);

        Bitmap lPicture = LoadImageTask.getImage(data.thumb);
        if (lPicture != null) {
            holder.mImageView.setImageBitmap(lPicture);
        } else {
            new LoadImageTask(holder.mImageView, data.thumb, mContext).execute();
        }

    }

    @Override
    public int getItemCount() {
        return mClipArrayList.size();
    }
}