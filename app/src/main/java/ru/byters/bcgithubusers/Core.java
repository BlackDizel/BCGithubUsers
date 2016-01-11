package ru.byters.bcgithubusers;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import ru.byters.bcgithubusers.ui.controllers.ControllerUserInfo;

public class Core extends Application {

    private ControllerUserInfo controllerUserInfo;

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        controllerUserInfo = new ControllerUserInfo(this);
    }

    public ControllerUserInfo getControllerUserInfo() {
        return controllerUserInfo;
    }
}
