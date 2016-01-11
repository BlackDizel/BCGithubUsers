package ru.byters.bcgithubusers.controllers.utils;

import ru.byters.bcgithubusers.model.UserInfo;

public interface OnDownloadedFollowers {
    void onDownloaded(String login, UserInfo data);
}