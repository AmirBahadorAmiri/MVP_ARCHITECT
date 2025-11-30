package com.example.mvparchitect.views.activities.splash;

import com.example.mvparchitect.views.bases.BaseContract;

public interface SplashContract {

    interface SplashView extends BaseContract.BaseView {
        void openMainActivity();
    }

    interface SplashPresenter extends BaseContract.BasePresenter<SplashView> {
        void nextActivity();
    }

}
