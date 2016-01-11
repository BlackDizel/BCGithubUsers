package ru.byters.bcgithubusers.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.byters.bcgithubusers.R;
import ru.byters.bcgithubusers.ui.adapters.UsersListAdapter;

public class FragmentList extends Fragment {

    public static final int FILTER_AH = 1;
    public static final int FILTER_IP = 2;
    public static final int FILTER_QZ = 3;
    private static final String EXTRA_FILTER_TYPE = "column-count";

    private int filterType;

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
        //recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new UsersListAdapter(filterType));
        return view;
    }

}
