package ru.byters.bcgithubusers.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.byters.bcgithubusers.Core;
import ru.byters.bcgithubusers.R;
import ru.byters.bcgithubusers.ui.adapters.UsersListAdapter;

public class FragmentList extends Fragment {

    private static final String EXTRA_FILTER_TYPE = "column-count";

    private int filterType;
    private UsersListAdapter adapter;

    public FragmentList() {
    }

    public static FragmentList newInstance(int filter_type) {
        FragmentList fragment = new FragmentList();
        Bundle args = new Bundle();
        args.putInt(EXTRA_FILTER_TYPE, filter_type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        filterType = -1;
        if (getArguments() != null) {
            filterType = getArguments().getInt(EXTRA_FILTER_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view;
        adapter = new UsersListAdapter(filterType
                , ((Core) view.getContext().getApplicationContext()).getControllerUserInfo());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new ScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore() {
                adapter.onScrolled();
            }
        });
        return view;
    }

    public void resetData() {
        if (adapter != null)
            adapter.resetData();
    }

    private abstract class ScrollListener extends RecyclerView.OnScrollListener {
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
            if (mLoading && (totalItemCount - previousItemCount > 1)) {
                mLoading = false;
                previousItemCount = totalItemCount;
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
}
