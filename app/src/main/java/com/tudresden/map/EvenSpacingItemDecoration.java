package com.tudresden.map;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class EvenSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spacing;

    public EvenSpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = state.getItemCount();

        // Apply spacing to all items except the last one
        if (position < itemCount - 1) {
            outRect.right = spacing;
        }
    }
}