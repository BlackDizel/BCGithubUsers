package ru.byters.bcgithubusers.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import ru.byters.bcgithubusers.R;
import ru.byters.bcgithubusers.model.UserInfo;
import ru.byters.bcgithubusers.ui.controllers.ControllerUserInfo;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    private int filterType;
    private ArrayList<UserInfo> data;
    private ControllerUserInfo controllerUserInfo;

    public UsersListAdapter(int filterType, ControllerUserInfo controllerUserInfo) {
        this.filterType = filterType;
        this.controllerUserInfo = controllerUserInfo;
        if (controllerUserInfo.isNoAdapter()) controllerUserInfo.setUsersListAdapter(this);

        data = controllerUserInfo.getModelUserInfo().getUserInfoStartWithFilter(filterType);
    }

    public int getFilterType() {
        return filterType;
    }

    public void onScrolled() {
        controllerUserInfo.getUsersMore();
    }

    public void resetData() {
        data = controllerUserInfo.getModelUserInfo().getUserInfoStartWithFilter(filterType);
        controllerUserInfo.setUsersListAdapter(this);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<UserInfo> downloaded) {
        if (downloaded == null) return;

        int position = this.data == null ? 0 : data.size();
        if (data == null) data = new ArrayList<>();
        data.addAll(downloaded);
        notifyItemRangeInserted(position, downloaded.size());
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
        return data == null ? 0 : data.size();
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
            UserInfo info = data.get(position);
            if (info != null) {
                tvNickname.setText(info.getLogin());
                ImageLoader.getInstance().displayImage(info.getAvatar_url(), ivAvatar);
                //todo implement followers info
            }
        }
    }
}
