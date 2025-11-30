package com.example.mvparchitect.views.bases;

public interface BaseContract {

    public interface BaseView {
    }

    public interface BasePresenter<T extends BaseView> {
        void onAttach(T baseView);

        void onDetach();
    }

}
