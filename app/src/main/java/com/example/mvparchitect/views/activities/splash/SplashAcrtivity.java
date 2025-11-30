package com.example.mvparchitect.views.activities.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.mvparchitect.R;
import com.example.mvparchitect.views.activities.main.MainActivity;
import com.example.mvparchitect.views.bases.BaseActivity;

public class SplashAcrtivity extends BaseActivity implements SplashContract.SplashView {

    SplashActivityPresenter splashActivityPresenter = new SplashActivityPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableEdge();
        setContentView(R.layout.activity_splash);
        setViewCompat();
        findViews();
        splashActivityPresenter.onAttach(this);
    }

    private void findViews() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashActivityPresenter.onDetach();
    }

    @Override
    public void openMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
