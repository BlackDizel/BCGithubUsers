package ru.byters.bcgithubusers.ui.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class ScrollListener extends RecyclerView.OnScrollListener {
    private boolean mLoading = false; // True if we are still waiting for the last set of data to load

    private int previousItemCount = 0; // The total number of items in the dataset after the last load

    private LinearLayoutManager mLinearLayoutManager;

    public ScrollListener(LinearLayoutManager linearLayoutManager) {
        mLinearLayoutManager = linearLayoutManager;
    }

    public abstract void onLoadMore();

    public void refresh() {
        previousItemCount = 0;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mLinearLayoutManager.getItemCount();
        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        // check if current total is greater than previous (diff should be greater than 1, for considering placeholder)
        // and if current total is equal to the total in server
        if (mLoading) {
            if (totalItemCount - previousItemCount > 1) {
                mLoading = false;
                previousItemCount = totalItemCount;
            }
        } else
            // check if the we've reached the end of the list,
            // and if the total items is less than the total items in the server
            if ((firstVisibleItem + visibleItemCount) >= totalItemCount) {
                onLoadMore();
                mLoading = true;
                previousItemCount = totalItemCount;
            }
    }

}