package ru.byters.bcgithubusers.ui.controllers;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;

import ru.byters.bcgithubusers.api.GithubService;
import ru.byters.bcgithubusers.model.ModelUserInfo;
import ru.byters.bcgithubusers.model.UserInfo;
import ru.byters.bcgithubusers.ui.adapters.UsersListAdapter;

public class ControllerUserInfo {

    private ModelUserInfo modelUserInfo;
    private UsersListAdapter usersListAdapter;
    private Context context;
    private boolean isLoading;

    public ControllerUserInfo(Context context) {
        isLoading = false;
        this.context = context;
        modelUserInfo = new ModelUserInfo(context);
        if (modelUserInfo.isNull())
            getUsers();
    }

    public ModelUserInfo getModelUserInfo() {
        return modelUserInfo;
    }

    public void setUsersListAdapter(UsersListAdapter usersListAdapter) {
        this.usersListAdapter = usersListAdapter;
    }

    private void writeData(ArrayList<UserInfo> result) {
        if (result != null) {

            if (context != null) modelUserInfo.setData(context, result);
            if (usersListAdapter != null) usersListAdapter.addData(
                    ModelUserInfo.getUserInfoStartWithFilter(usersListAdapter.getFilterType(), result));
        }
        isLoading = false;
    }

    private void addData(ArrayList<UserInfo> result) {
        if (result != null) {
            if (context != null) modelUserInfo.addData(context, result);
            if (usersListAdapter != null) usersListAdapter.addData(
                    ModelUserInfo.getUserInfoStartWithFilter(usersListAdapter.getFilterType(), result));
        }
        isLoading = false;
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

    public boolean isNoAdapter() {
        return usersListAdapter == null;
    }
}
