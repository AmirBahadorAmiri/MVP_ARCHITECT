package com.example.mvparchitect.views.activities.noteview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.mvparchitect.R;
import com.example.mvparchitect.models.Note;
import com.example.mvparchitect.tools.logger.Logger;
import com.example.mvparchitect.tools.roomdb.RoomDB;
import com.example.mvparchitect.views.activities.main.MainActivity;
import com.example.mvparchitect.views.bases.BaseActivity;

import java.util.Objects;

public class NoteActivity extends BaseActivity implements NoteContract.NoteView {

    NoteActivityPresenter noteActivityPresenter;
    AppCompatEditText activity_noteview_note_title, activity_noteview_note_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableEdge();
        setContentView(R.layout.activity_noteview);
        setViewCompat();
        findViews();
        setupViews();
        noteActivityPresenter = new NoteActivityPresenter(RoomDB.getRoomDB(this).getNoteDao(), getIntent().getParcelableExtra(MainActivity.EXTRA_KEY_NOTE));
        noteActivityPresenter.onAttach(this);
    }

    private void findViews() {
        activity_noteview_note_title = findViewById(R.id.activity_noteview_note_title);
        activity_noteview_note_text = findViewById(R.id.activity_noteview_note_text);
    }

    private void setupViews() {
        findViewById(R.id.activity_noteview_toolbar_delete_icon).setOnClickListener(v -> noteActivityPresenter.onDeleteCLick());

        findViewById(R.id.activity_noteview_toolbar_back_icon).setOnClickListener(v -> noteActivityPresenter.onBackClick());

        findViewById(R.id.activity_noteview_toolbar_save_icon).setOnClickListener(v -> noteActivityPresenter.onSaveClick(getTitleEditText(), getTextEditText()));

    }

    public String getTextEditText() {
        return Objects.requireNonNull(activity_noteview_note_text.getText()).toString();
    }

    public String getTitleEditText() {
        return Objects.requireNonNull(activity_noteview_note_title.getText()).toString();
    }

    @Override
    public void showNote(Note note) {
        activity_noteview_note_title.setText(note.getTitle());
        activity_noteview_note_text.setText(note.getNote());
    }

    @Override
    public void saveNote(int result_code, Note new_note) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_KEY_NOTE, new_note);
        setResult(result_code, intent);
        finish();
    }

    @Override
    public void deleteNote(Note delete_note) {
        Logger.logd("Note Activity , delete_note id: " + delete_note.getId());
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_KEY_NOTE, delete_note);
        setResult(MainActivity.RESULT_CODE_DELETE_NOTE, intent);
        finish();
    }

    @Override
    public void showToast(String s, int length) {
        Toast.makeText(this, s, length).show();
    }


    @Override
    public void onFinishActivity() {
        finish();
    }
}
