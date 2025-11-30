package com.example.mvparchitect.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvparchitect.R;
import com.example.mvparchitect.models.Note;
import com.example.mvparchitect.tools.logger.Logger;
import com.example.mvparchitect.tools.roomdb.NoteDao;
import com.example.mvparchitect.views.activities.main.MainContract;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    public List<Note> noteList = new ArrayList<>();
    public MainContract.MainView mainView;
    public NoteDao noteDao;

    public NoteAdapter(MainContract.MainView mainView, NoteDao noteDao) {
        this.mainView = mainView;
        this.noteDao = noteDao;
    }

    public void addNotes(List<Note> notes) { /* Not implemented */}

    public void updateNote(Note update_note) {
        Logger.logd("note adapter updateNote id: " + update_note.getId());
        for (int pos = 0, size = noteList.size(); pos < size; pos++) {
            if (noteList.get(pos).getId().equals(update_note.getId())) {
                int finalPos = pos;
                Observable.timer(500, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<>() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                            }

                            @Override
                            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Long aLong) {
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                                noteList.remove(finalPos);
                                notifyItemRemoved(finalPos);
                                Observable.timer(500, TimeUnit.MILLISECONDS)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<>() {
                                            @Override
                                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                            }

                                            @Override
                                            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Long aLong) {
                                            }

                                            @Override
                                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                            }

                                            @Override
                                            public void onComplete() {
                                                noteList.add(finalPos, update_note);
                                                notifyItemInserted(finalPos);
                                            }
                                        });
                            }
                        });
            }
        }
    }

    public void deleteNote(Note deleted_note) {
        Logger.logd("note adapter deleteNote id: " + deleted_note.getId());
        for (int pos = 0, size = noteList.size(); pos < size; pos++) {
            if (noteList.get(pos).getId().equals(deleted_note.getId())) {
                int finalPos = pos;
                Observable.timer(500, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<>() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                            }

                            @Override
                            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Long aLong) {
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                                noteList.remove(finalPos);
                                notifyItemRemoved(finalPos);
                            }
                        });
            }
        }
    }

    public void addNote(Note new_note) {
        Logger.logd("adapter add note");
        Logger.logd("adapter note id: " + new_note.getId());
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Long aLong) {
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        noteList.add(new_note);
                        notifyItemInserted(noteList.size() - 1);
                        mainView.scrollToPosition(noteList.size() - 1);
                    }
                });
    }

    public void deleteAllNotes() {
        if (noteList.isEmpty()) {
            mainView.showToast("لیست نوت ها خالی میباشد", Toast.LENGTH_SHORT);
        } else {
            noteDao.deleteAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        }

                        @Override
                        public void onComplete() {
                            notifyItemRangeRemoved(0, noteList.size());
                            noteList.clear();
                            mainView.showToast("تمامی نوت ها پاک شدند", Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        }
                    });
        }

    }

    public void showNotes(List<Note> notes) {
        notifyItemRangeRemoved(0, noteList.size());
        noteList.clear();
        noteList.addAll(notes);
        notifyItemRangeInserted(0, noteList.size());
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.bindViews(holder);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class NoteHolder extends RecyclerView.ViewHolder {

        AppCompatTextView item_task_textview;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(View itemView) {
            item_task_textview = itemView.findViewById(R.id.item_task_textview);

        }

        public void bindViews(NoteHolder holder) {
            item_task_textview.setText(noteList.get(holder.getAbsoluteAdapterPosition()).getTitle());
            itemView.setOnClickListener(v -> mainView.openExistNoteView(noteList.get(holder.getAbsoluteAdapterPosition())));
        }
    }
}
