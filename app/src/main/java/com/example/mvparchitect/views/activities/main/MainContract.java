package com.example.mvparchitect.views.activities.main;

import com.example.mvparchitect.models.Note;
import com.example.mvparchitect.views.bases.BaseContract;

import java.util.List;

public interface MainContract {

    interface MainView extends BaseContract.BaseView {
        void showNotes(List<Note> notes);

        void deleteNote(Note note);

        void deleteAllNotes();

        void addNote(Note note);

        void addNotes(List<Note> notes);

        void openExistNoteView(Note note);
        void openNewNoteView();

        void showToast(String s, int length);

        void scrollToPosition(int pos);

    }

    interface MainPresenter extends BaseContract.BasePresenter<MainView> {

        void onDeleteAllNote();

        void fabClickListener();

        void onSearch(String s);

        void showAllNote();

    }

}
