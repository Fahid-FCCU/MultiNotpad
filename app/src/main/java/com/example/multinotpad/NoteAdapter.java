package com.example.multinotpad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "NoteAdapter";
    private final List<String> notes;
    private final MainActivity mainAct;

    public NoteAdapter(List<String> notes, MainActivity mainAct) {
        this.notes = notes;
        this.mainAct = mainAct;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //set the title and Text to Adapter Row
        holder.title.setText(notes.get(position));
        holder.text.setText(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
