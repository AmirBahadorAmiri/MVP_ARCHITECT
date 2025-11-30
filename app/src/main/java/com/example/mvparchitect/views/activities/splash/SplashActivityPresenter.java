package com.example.mvparchitect.views.activities.splash;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashActivityPresenter implements SplashContract.SplashPresenter {

    SplashContract.SplashView splashView;

    @Override
    public void nextActivity() {

        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override public void onSubscribe(@NonNull Disposable d) {}
                    @Override public void onNext(@NonNull Long aLong) {}
                    @Override public void onError(@NonNull Throwable e) {}
                    @Override
                    public void onComplete() {
                        splashView.openMainActivity();
                    }
                });

    }

    @Override
    public void onAttach(SplashContract.SplashView baseView) {
        this.splashView = baseView;
        nextActivity();
    }

    @Override
    public void onDetach() {
        this.splashView = null;
    }
}
