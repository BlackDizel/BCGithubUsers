package ru.byters.bcgithubusers.model;


import android.content.Context;

import java.util.ArrayList;

import ru.byters.bcgithubusers.controllers.ControllerStorage;

public class ModelUserInfo {

    public static final int FILTER_AH = 1;
    public static final int FILTER_IP = 2;
    public static final int FILTER_QZ = 3;

    private ArrayList<UserInfo> listUserInfo;
    private int lastLogin;

    public ModelUserInfo(Context context) {
        listUserInfo = (ArrayList<UserInfo>) ControllerStorage.readObjectFromFile(context, ControllerStorage.USERINFO);
        Object o = ControllerStorage.readObjectFromFile(context, ControllerStorage.LASTLOGIN);
        lastLogin = o == null ? 0 : (int) o;
    }

    public static ArrayList<UserInfo> getUserInfoStartWithFilter(int filter, ArrayList<UserInfo> data) {
        if (data == null) return null;
        ArrayList<UserInfo> list = new ArrayList<>();
        for (UserInfo info : data) {

            if (info.getLogin() == null) continue;
            Character c = info.getLogin().toUpperCase().charAt(0);

            if ((filter == FILTER_AH
                    && c >= 'A' && c <= 'H')
                    || (filter == FILTER_IP
                    && c >= 'I' && c <= 'P')
                    || (filter == FILTER_QZ
                    && c >= 'Q' && c <= 'Z'))
                list.add(info);
        }

        if (list.size() > 0)
            return list;

        return null;
    }

    public boolean isNull() {
        return listUserInfo == null;
    }

    public ArrayList<UserInfo> getUserInfoStartWithFilter(int filter) {
        return getUserInfoStartWithFilter(filter, listUserInfo);
    }

    public void setData(Context context, ArrayList<UserInfo> data) {
        if (data == null
                || data.size() == 0
                || data.get(data.size() - 1).getLogin() == null) {
            listUserInfo = null;
            lastLogin = -1;
        } else {
            listUserInfo = data;
            lastLogin = data.get(data.size() - 1).getId();
        }

        ControllerStorage.writeObjectToFile(context, data, ControllerStorage.USERINFO);
        ControllerStorage.writeObjectToFile(context, lastLogin, ControllerStorage.LASTLOGIN);
    }

    public void addData(Context context, ArrayList<UserInfo> data) {
        if (data == null
                || data.size() == 0
                || data.get(data.size() - 1).getId() == 0) {
            return;
        }

        listUserInfo.addAll(data);
        lastLogin = data.get(data.size() - 1).getId();

        ControllerStorage.writeObjectToFile(context, data, ControllerStorage.USERINFO);
        ControllerStorage.writeObjectToFile(context, lastLogin, ControllerStorage.LASTLOGIN);
    }

    public void clearData(Context context) {
        lastLogin = 0;
        listUserInfo = null;
        ControllerStorage.RemoveFile(context, ControllerStorage.USERINFO);
        ControllerStorage.RemoveFile(context, ControllerStorage.LASTLOGIN);
    }

    public int getLastLogin() {
        return lastLogin;
    }

    public void updateData(UserInfo data) {
        if (listUserInfo == null || data == null) return;
        for (UserInfo info : listUserInfo)
            if (info.getLogin().equals(data.getLogin())) {
                info.setFollowers(data.getFollowers());
                info.setFollowing(data.getFollowing());
            }
    }
}
