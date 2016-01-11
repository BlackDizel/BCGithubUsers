package ru.byters.bcgithubusers.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.byters.bcgithubusers.R;
import ru.byters.bcgithubusers.ui.adapters.UsersListAdapter;
import ru.byters.bcgithubusers.controllers.ControllerUserInfo;
import ru.byters.bcgithubusers.ui.utils.ScrollListener;

public class FragmentList extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String EXTRA_FILTER_TYPE = "filter_type";
    private ControllerUserInfo controllerUserInfo;

    private int filterType;
    private UsersListAdapter adapter;
    private ScrollListener scrollListener;

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

        controllerUserInfo = new ControllerUserInfo(view.getContext());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        adapter = new UsersListAdapter(filterType, controllerUserInfo);
        recyclerView.setAdapter(adapter);

        scrollListener = new ScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore() {
                controllerUserInfo.getUsersMore();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srlList);
        refreshLayout.setOnRefreshListener(this);

        controllerUserInfo.setRefreshLayout(refreshLayout);
        controllerUserInfo.setUsersListAdapter(adapter);

        return view;
    }

    public void resetData() {
        if (adapter != null)
            adapter.resetData();
    }

    @Override
    public void onRefresh() {
        resetData();
        scrollListener.refresh();
        controllerUserInfo.setIsCancelled(getActivity(), true);
    }
}
