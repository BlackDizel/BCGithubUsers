package ru.byters.bcgithubusers.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;

import ru.byters.bcgithubusers.api.GithubService;
import ru.byters.bcgithubusers.model.ModelUserInfo;
import ru.byters.bcgithubusers.model.UserInfo;
import ru.byters.bcgithubusers.ui.adapters.UsersListAdapter;

public class ControllerUserInfo {

    private static ModelUserInfo modelUserInfo;
    private static boolean isLoading;
    private static boolean isCancelled;
    private UsersListAdapter usersListAdapter;
    private SwipeRefreshLayout refreshLayout;
    private Context context;

    public ControllerUserInfo(Context context) {
        isLoading = false;
        this.context = context;
        if (modelUserInfo == null) {
            modelUserInfo = new ModelUserInfo(context);
            if (modelUserInfo.isNull())
                getUsers();
        }
    }

    public ModelUserInfo getModelUserInfo() {
        return modelUserInfo;
    }

    public void setUsersListAdapter(UsersListAdapter usersListAdapter) {
        this.usersListAdapter = usersListAdapter;
    }

    private void writeData(ArrayList<UserInfo> result) {
        if (result != null) {
            if (refreshLayout != null) refreshLayout.setRefreshing(false);
            isCancelled = false;
            if (context != null) modelUserInfo.setData(context, result);
            if (usersListAdapter != null) usersListAdapter.addData(
                    ModelUserInfo.getUserInfoStartWithFilter(usersListAdapter.getFilterType(), result));
        }
        isLoading = false;
    }

    private void addData(ArrayList<UserInfo> result) {
        if (result != null && !isCancelled) {
            if (context != null) modelUserInfo.addData(context, result);
            if (usersListAdapter != null) usersListAdapter.addData(
                    ModelUserInfo.getUserInfoStartWithFilter(usersListAdapter.getFilterType(), result));
        }
        isLoading = false;
        if (isCancelled)
            getUsers();
    }

    public void getUsers() {
        //todo add v23 check permission
        if (isLoading) return;
        isLoading = true;
        new AsyncTask<Void, Void, ArrayList<UserInfo>>() {
            @Override
            protected ArrayList<UserInfo> doInBackground(Void... params) {
                try {
                    return GithubService.getApi().getUsers().execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;

            }

            @Override
            protected void onPostExecute(ArrayList<UserInfo> result) {
                super.onPostExecute(result);
                ControllerUserInfo.this.writeData(result);
            }
        }.execute();
    }

    public void getUsersMore() {
        //todo add v23 check permission
        if (isLoading) return;
        isLoading = true;
        new AsyncTask<Void, Void, ArrayList<UserInfo>>() {
            @Override
            protected ArrayList<UserInfo> doInBackground(Void... params) {
                try {
                    return GithubService.getApi().getUsers(modelUserInfo.getLastLogin()).execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<UserInfo> result) {
                super.onPostExecute(result);
                ControllerUserInfo.this.addData(result);
            }
        }.execute();
    }

    public void setRefreshLayout(SwipeRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    public void setIsCancelled(Context context, boolean isCancelled) {
        ControllerUserInfo.isCancelled = isCancelled;
        if (isCancelled) {
            modelUserInfo.clearData(context);
            getUsers();
        }
    }
}
