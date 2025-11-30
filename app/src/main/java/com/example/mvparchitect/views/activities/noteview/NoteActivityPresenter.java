package com.example.mvparchitect.views.activities.noteview;

import android.widget.Toast;

import com.example.mvparchitect.models.Note;
import com.example.mvparchitect.tools.logger.Logger;
import com.example.mvparchitect.tools.roomdb.NoteDao;
import com.example.mvparchitect.views.activities.main.MainActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NoteActivityPresenter implements NoteContract.NotePresenter {

    NoteDao noteDao;
    NoteContract.NoteView noteView;
    Note old_note;

    public NoteActivityPresenter(NoteDao noteDao, Note new_note) {
        this.noteDao = noteDao;
        this.old_note = new_note;
    }

    @Override
    public void onAttach(NoteContract.NoteView baseView) {
        this.noteView = baseView;
        if (old_note != null) {
            noteView.showNote(old_note);
        }
    }

    @Override
    public void onDetach() {
        this.noteView = null;
    }

    @Override
    public void onDeleteCLick() {
        if (old_note == null) {
            noteView.showToast("نوت ساخته شده که دیلیت نمیشه :)", Toast.LENGTH_SHORT);
        } else {
            Logger.logd("note presenter ondelete , old_note id: " + old_note.getId());
            noteDao.delete(old_note)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                        }

                        @Override
                        public void onComplete() {
                            Logger.logd("note presenter onComplete , old_note id: " + old_note.getId());
                            noteView.deleteNote(old_note);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                        }
                    });
        }
    }

    @Override
    public void onSaveClick(String title, String text) {
        if (old_note == null) {
            Note new_note = new Note(title, text);
            noteDao.insert(new_note)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                        }

                        @Override
                        public void onSuccess(@NonNull Long aLong) {
                            Logger.logd("note presenter onSuccess");
                            Logger.logd("first new_note id: " + new_note.getId());
                            new_note.setId(aLong);
                            noteView.saveNote(MainActivity.RESULT_CODE_ADD_NOTE, new_note);
                            Logger.logd("last new_note id: " + new_note.getId());
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Logger.logd("note presenter onError");
                            Logger.logd(e.getMessage());
                        }
                    });
        } else {
            old_note.setTitle(title);
            old_note.setNote(text);
            noteDao.update(old_note)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {}

                        @Override
                        public void onComplete() {
                            Logger.logd("note presenter old note onComplete");
                            noteView.saveNote(MainActivity.RESULT_CODE_UPDATE_NOTE, old_note);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Logger.logd("note presenter old note onError");
                            Logger.logd(e.getMessage());
                        }
                    });
        }
    }

    @Override
    public void onBackClick() {
        this.noteView.onFinishActivity();
    }

}
