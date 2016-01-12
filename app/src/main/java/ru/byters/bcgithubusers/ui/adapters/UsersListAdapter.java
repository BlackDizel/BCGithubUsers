package ru.byters.bcgithubusers.ui.adapters;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;

import ru.byters.bcgithubusers.R;
import ru.byters.bcgithubusers.api.GithubService;
import ru.byters.bcgithubusers.controllers.ControllerUserInfo;
import ru.byters.bcgithubusers.controllers.utils.OnDownloadedFollowers;
import ru.byters.bcgithubusers.model.UserInfo;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    private int filterType;
    private ArrayList<UserInfo> data;
    private ControllerUserInfo controllerUserInfo;

    public UsersListAdapter(int filterType, ControllerUserInfo controllerUserInfo) {
        this.filterType = filterType;
        this.controllerUserInfo = controllerUserInfo;
        if (filterType != 0)
            data = controllerUserInfo.getModelUserInfo().getUserInfoStartWithFilter(filterType);
    }

    public int getFilterType() {
        return filterType;
    }

    public void resetData() {
        if (filterType != 0)
            data = controllerUserInfo.getModelUserInfo().getUserInfoStartWithFilter(filterType);
        else data = null;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements OnDownloadedFollowers {
        private final TextView tvNickname;
        private final TextView tvFollowInfo;
        private final ImageView ivAvatar;
        private String login;

        public ViewHolder(View view) {
            super(view);
            tvNickname = (TextView) view.findViewById(R.id.tvNickname);
            tvFollowInfo = (TextView) view.findViewById(R.id.tvFollowInfo);
            ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);
        }

        public void setData(int position) {
            UserInfo info = data.get(position);
            if (info != null) {
                login = info.getLogin();
                tvNickname.setText(info.getLogin());
                ImageLoader.getInstance().displayImage(info.getAvatar_url(), ivAvatar);
                tvFollowInfo.setText(String.format("%d/%d", info.getFollowers(), info.getFollowing()));
                if (info.getFollowing() == 0 || info.getFollowers() == 0)
                    new TaskFollowers(login, this).execute();
            }
        }

        @Override
        public void onDownloaded(String login, UserInfo data) {
            controllerUserInfo.getModelUserInfo().updateData(data);
            if (this.login.equals(login) && data != null)
                tvFollowInfo.setText(String.format("%d/%d", data.getFollowers(), data.getFollowing()));
        }

        private class TaskFollowers extends AsyncTask<Void, Void, UserInfo> {

            OnDownloadedFollowers listener;
            String login;

            public TaskFollowers(String login, OnDownloadedFollowers listener) {
                this.login = login;
                this.listener = listener;
            }

            @Override
            protected UserInfo doInBackground(Void... params) {
                try {
                    return GithubService.getApi().getUserInfo(login).execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(UserInfo userInfo) {
                super.onPostExecute(userInfo);
                if (listener != null) listener.onDownloaded(login, userInfo);
            }
        }
    }
}
