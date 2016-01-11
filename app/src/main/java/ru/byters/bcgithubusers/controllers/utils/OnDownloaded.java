package ru.byters.bcgithubusers.controllers.utils;

import java.util.ArrayList;

import ru.byters.bcgithubusers.model.UserInfo;

public interface OnDownloaded {
    void onDownloaded(String query, ArrayList<UserInfo> data);
}