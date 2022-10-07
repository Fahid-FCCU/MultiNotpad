package com.example.multinotpad;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView title,text,saveTime;
    public MyViewHolder(@NonNull View view) {
        super(view);
        title = view.findViewById(R.id.rowTitle);
        saveTime = view.findViewById(R.id.rowTime);
        text = view.findViewById(R.id.rowText);
    }
}
