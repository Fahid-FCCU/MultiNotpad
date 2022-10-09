package com.example.multinotepad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class EditActivity extends AppCompatActivity {
    private static final String TAG = "TAG";

    private EditText title;
    private EditText text;
    private boolean isEditMode = false;

    private String initialTitle = "";
    private String initialText = "";
    private int editPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        title=findViewById(R.id.etTitleNote);
        text =findViewById(R.id.etTextNote);

        isEditMode = getIntent().getBooleanExtra("edit_note",false);
        if (isEditMode) {
            editPosition = getIntent().getIntExtra("edit_note_position",0);
            Note note = MainActivity.allNotes.get(editPosition);
            title.setText(note.getTitle());
            text.setText(note.getText());
            initialTitle = note.title;
            initialText = note.text;
        }
    }

    private void saveNote(){
        String titleStr = title.getText().toString();
        String textStr = text.getText().toString();
        if(!titleStr.isEmpty()){
            if (isEditMode) {
                //edit note
                if (initialTitle.equals(title.getText().toString()) && initialText.equals(text.getText().toString())) {
                    finish();
                } else {
                    // update not because it's changed
                    Note note = MainActivity.allNotes.get(editPosition);
                    note.title = title.getText().toString();
                    note.text = text.getText().toString();
                    note.date = new Date();
                    finish();
                }


            } else {
                // add new note
                Note note = new Note(titleStr,textStr,new Date());
                MainActivity.allNotes.add(note);
                finish();
            }

        } else {
            Toast.makeText(this, "Un-titled activity was not saved.", Toast.LENGTH_SHORT).show();
            finish();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSave:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (initialTitle.equals(title.getText().toString()) && initialText.equals(text.getText().toString())) {
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You have unsaved changes")
                    .setCancelable(false)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            saveNote();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
