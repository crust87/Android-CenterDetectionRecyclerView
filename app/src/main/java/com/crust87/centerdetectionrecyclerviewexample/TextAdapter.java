package com.crust87.centerdetectionrecyclerviewexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mabi on 2016. 1. 15..
 */
public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ClipViewHolder> {

    private Context mContext;

    public static class ClipViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ClipViewHolder(View view, TextView textView) {
            super(view);
            mTextView = textView;
        }
    }

    @Override
    public TextAdapter.ClipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);

        TextView textView = (TextView) view.findViewById(R.id.text);

        return new ClipViewHolder(view, textView);
    }

    @Override
    public void onBindViewHolder(ClipViewHolder holder, int position) {
        holder.mTextView.setText("I AM NOT");
    }

    @Override
    public int getItemCount() {
        return 50;
    }
}