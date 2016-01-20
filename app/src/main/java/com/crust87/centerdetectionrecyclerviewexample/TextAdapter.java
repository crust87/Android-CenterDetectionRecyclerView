/*
 * Android-CenterDetectionRecyclerView
 * https://github.com/crust87/Android-FFmpegExecutor
 *
 * Mabi
 * crust87@gmail.com
 * last modify 2016-01-15
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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by mabi on 2016. 1. 15..
 */
public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ClipViewHolder> {

    public static int BACKGROUND_COLOR = Color.parseColor("#7e7e7e");

    public static class ClipViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout mLayout;
        public TextView mTextView;

        public ClipViewHolder(View view, FrameLayout layout, TextView textView) {
            super(view);
            mLayout = layout;
            mTextView = textView;
        }
    }

    @Override
    public TextAdapter.ClipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);

        FrameLayout layout = (FrameLayout) view.findViewById(R.id.layout);
        TextView textView = (TextView) view.findViewById(R.id.text);

        return new ClipViewHolder(view, layout, textView);
    }

    @Override
    public void onBindViewHolder(ClipViewHolder holder, int position) {
        holder.mLayout.setBackgroundColor(BACKGROUND_COLOR);
        holder.mTextView.setText("I AM NOT");
    }

    @Override
    public int getItemCount() {
        return 50;
    }
}