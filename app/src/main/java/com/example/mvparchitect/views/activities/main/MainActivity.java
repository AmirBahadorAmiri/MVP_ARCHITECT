package com.example.mvparchitect.views.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvparchitect.R;
import com.example.mvparchitect.adapters.NoteAdapter;
import com.example.mvparchitect.models.Note;
import com.example.mvparchitect.tools.logger.Logger;
import com.example.mvparchitect.tools.roomdb.RoomDB;
import com.example.mvparchitect.views.activities.noteview.NoteActivity;
import com.example.mvparchitect.views.bases.BaseActivity;

import java.util.List;
import java.util.Objects;

public class MainActivity extends BaseActivity implements MainContract.MainView {

    public static final String EXTRA_KEY_NOTE = "NOTE_EXTRA";
    MainActivityPresenter mainActivityPresenter;
    RecyclerView activity_main_tasks_recyclerview;
    NoteAdapter noteAdapter;

    AppCompatEditText activity_main_searchbar;

    public static final int REQUEST_CODE = 999;
    public static final int RESULT_CODE_ADD_NOTE = 1001;
    public static final int RESULT_CODE_UPDATE_NOTE = 1002;
    public static final int RESULT_CODE_DELETE_NOTE = 1003;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableEdge();
        setContentView(R.layout.activity_main);
        setViewCompat();
        findViews();
        setupViews();
        mainActivityPresenter = new MainActivityPresenter(RoomDB.getRoomDB(this).getNoteDao());
        mainActivityPresenter.onAttach(this);
    }

    private void findViews() {
        activity_main_tasks_recyclerview = findViewById(R.id.activity_main_tasks_recyclerview);
        activity_main_searchbar = findViewById(R.id.activity_main_searchbar);
    }

    private void setupViews() {
        noteAdapter = new NoteAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        activity_main_tasks_recyclerview.setLayoutManager(linearLayoutManager);
        activity_main_tasks_recyclerview.setAdapter(noteAdapter);

        findViewById(R.id.activity_main_icon_delete).setOnClickListener(v -> mainActivityPresenter.onDeleteAllNote(noteAdapter.getItemCount()));
        findViewById(R.id.activity_main_icon_add).setOnClickListener(v -> mainActivityPresenter.fabClickListener());

        activity_main_searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mainActivityPresenter.onSearch(Objects.requireNonNull(s.toString()));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showNotes(List<Note> notes) {
        noteAdapter.showNotes(notes);
    }

    @Override
    public void deleteNote(Note note) {
        noteAdapter.deleteNote(note);
    }

    @Override
    public void deleteAllNotes() {
        noteAdapter.deleteAllNotes();
    }

    @Override
    public void addNote(Note note) {
        noteAdapter.addNote(note);
    }

    @Override
    public void addNotes(List<Note> notes) {
        noteAdapter.addNotes(notes);
    }

    @Override
    public void openExistNoteView(Note note) {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra(EXTRA_KEY_NOTE, note);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void openNewNoteView() {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void showToast(String s, int length) {
        Toast.makeText(this, s, length).show();
    }

    @Override
    public void scrollToPosition(int pos) {
        activity_main_tasks_recyclerview.scrollToPosition(pos);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            Note result_note = Objects.requireNonNull(data).getParcelableExtra(EXTRA_KEY_NOTE);
            Logger.logd("MainActivity onActivityResult");
            Logger.logd("result_note id: " + result_note.getId());
            if (resultCode == RESULT_CODE_ADD_NOTE) {
                noteAdapter.addNote(result_note);
            } else if (resultCode == RESULT_CODE_UPDATE_NOTE) {
                noteAdapter.updateNote(result_note);
            } else if (resultCode == RESULT_CODE_DELETE_NOTE) {
                noteAdapter.deleteNote(result_note);
            }
        } else {
        }
    }
}
