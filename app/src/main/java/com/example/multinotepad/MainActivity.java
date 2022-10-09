package com.example.multinotepad;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Note> allNotes;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private boolean isLoadedData = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allNotes = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        noteAdapter = new NoteAdapter(allNotes,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAdd:
                startActivity(new Intent(MainActivity.this,EditActivity.class));
                return true;
            case R.id.itemAbout:
                    startActivity(new Intent(MainActivity.this,AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isLoadedData) {
            new LoadJSONAsync().execute();
        }
        notifyDataChange();
    }

    public class LoadJSONAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            Log.d(TAG, "loadFile: Loading JSON File");
            try {
                InputStream is = getApplicationContext().openFileInput(getString(R.string.file_name));
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                JSONObject jsonObject = new JSONObject(sb.toString());
                JSONArray array = (JSONArray) jsonObject.get("Notes");
                for (int i =0;i<array.length();i++){

                    String title = array.getJSONObject(i).getString("title");
                    String text= array.getJSONObject(i).getString("text");
                    Long date = array.getJSONObject(i).getLong("date");
                    Note note = new Note(title,text, new Date(date));
                    allNotes.add(note);
                }

            } catch (FileNotFoundException e) {
                Log.d("MultiNotepad", getString(R.string.no_file));
            } catch (Exception e) {
                e.printStackTrace();
            }
           return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            isLoadedData = true;
            notifyDataChange();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNotes();
    }

    private void saveNotes(){
        Log.d(TAG, "saveNote: Saving JSON File");
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);
            PrintWriter printWriter = new PrintWriter(fos);

            JSONArray jsArray = new JSONArray();

            for (Note note: allNotes) {
                JSONObject noteObject = new JSONObject();
                noteObject.put("title",note.getTitle());
                noteObject.put("text",note.getText());
                noteObject.put("date",note.getDate().getTime());
                jsArray.put(noteObject);
            }

            JSONObject mainObject = new JSONObject();
            mainObject.put("Notes",jsArray);
            printWriter.write(mainObject.toString());
            printWriter.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notifyDataChange() {
        // sort by time before updating adapter
        Collections.sort(allNotes, new Comparator<Note>() {
            @Override
            public int compare(Note note, Note t1) {
                if(note.date.getTime() < t1.date.getTime())
                    return 1;

                return -1;
            }
        });
        noteAdapter.notifyDataSetChanged();
    }

}