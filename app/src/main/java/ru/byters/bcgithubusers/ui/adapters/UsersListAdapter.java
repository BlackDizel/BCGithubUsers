package ru.byters.bcgithubusers.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.byters.bcgithubusers.R;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    private int filterType;

    public UsersListAdapter(int filterType) {
        this.filterType = filterType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_userlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        //todo implement
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNickname;
        private final TextView tvFollowInfo;
        private final ImageView ivAvatar;

        public ViewHolder(View view) {
            super(view);
            tvNickname = (TextView) view.findViewById(R.id.tvNickname);
            tvFollowInfo = (TextView) view.findViewById(R.id.tvFollowInfo);
            ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);
        }

        public void setData(int position) {
            //todo implement
        }


    }
}
