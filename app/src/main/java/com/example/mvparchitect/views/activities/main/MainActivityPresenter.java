package com.example.mvparchitect.views.activities.main;

import com.example.mvparchitect.models.Note;
import com.example.mvparchitect.tools.roomdb.NoteDao;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivityPresenter implements MainContract.MainPresenter {

    private MainContract.MainView mainView;
    public NoteDao noteDao;

    public MainActivityPresenter(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    public void onAttach(MainContract.MainView baseView) {
        this.mainView = baseView;
        showAllNote();
    }

    @Override
    public void onDetach() {
        this.mainView = null;
    }


    @Override
    public void onDeleteAllNote() {
        this.mainView.deleteAllNotes();
    }

    @Override
    public void fabClickListener() {
        this.mainView.openNewNoteView();
    }

    @Override
    public void onSearch(String s) {
        if (s.isEmpty()) {
            showAllNote();
        } else {
            noteDao.search(s)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                        }

                        @Override
                        public void onSuccess(@NonNull List<Note> notes) {
                            mainView.showNotes(notes);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                        }
                    });
        }
    }

    @Override
    public void showAllNote() {
        noteDao.selectAllNote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull List<Note> notes) {
                        mainView.showNotes(notes);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }

}
