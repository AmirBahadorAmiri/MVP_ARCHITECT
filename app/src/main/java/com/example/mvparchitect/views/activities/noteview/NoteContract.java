package com.example.mvparchitect.views.activities.noteview;

import com.example.mvparchitect.models.Note;
import com.example.mvparchitect.views.bases.BaseContract;

public interface NoteContract {

    interface NoteView extends BaseContract.BaseView {

        void showNote(Note note);

        void saveNote(int request_code,Note note);

        void deleteNote(Note note);

        void showToast(String s, int length);

        void onFinishActivity();

    }

    interface NotePresenter extends BaseContract.BasePresenter<NoteView> {

        void onDeleteCLick();

        void onBackClick();

        void onSaveClick(String title, String text);

    }

}