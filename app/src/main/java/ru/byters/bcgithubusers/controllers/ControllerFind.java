package ru.byters.bcgithubusers.controllers;


import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;

import ru.byters.bcgithubusers.api.GithubService;
import ru.byters.bcgithubusers.controllers.utils.OnDownloaded;
import ru.byters.bcgithubusers.model.UserInfo;
import ru.byters.bcgithubusers.ui.adapters.UsersListAdapter;

public class ControllerFind implements OnDownloaded {

    UsersListAdapter adapter;
    private String query;

    public ControllerFind(UsersListAdapter adapter) {
        this.adapter = adapter;
    }

    public void search(String query) {
        this.query = query;
        adapter.resetData();
        new TaskFind(query, this).execute();
    }

    @Override
    public void onDownloaded(String query, ArrayList<UserInfo> data) {
        if (query.equals(this.query) && data != null)
            adapter.addData(data);
    }

    public void loadMore() {
        //todo implement
    }

    private class TaskFind extends AsyncTask<Void, Void, ArrayList<UserInfo>> {

        String query;
        OnDownloaded listener;

        public TaskFind(String query, OnDownloaded listener) {
            this.query = query;
            this.listener = listener;
        }

        @Override
        protected ArrayList<UserInfo> doInBackground(Void... params) {
            //todo implement v23 check permission
            try {
                return GithubService.getApi().searchUsers(query).execute().body().getItems();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<UserInfo> userInfos) {
            super.onPostExecute(userInfos);
            listener.onDownloaded(query, userInfos);
        }
    }
}