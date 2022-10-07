package com.example.multinotpad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class EditActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Note note;
    private EditText title;
    private EditText text;
    JSONAsyn jsonAsyn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        title=findViewById(R.id.etTitleNote);
        text =findViewById(R.id.etTextNote);
        jsonAsyn=new JSONAsyn();

    }
    @Override
    protected void onPause() {
        String titleStr = title.getText().toString();
        String textStr = text.getText().toString();
        //validation
        if(!titleStr.isEmpty() && !textStr.isEmpty()){
            note = new Note(titleStr,textStr);
        }
        saveNote();   //JSon Data Saving function
        super.onPause();
    }
    private void saveNote(){
        Log.d(TAG, "saveNote: Saving JSON File");
        try{
            FileOutputStream fos = getApplicationContext().
                    openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);
            PrintWriter printWriter = new PrintWriter(fos);
            printWriter.write(note.toJSON());
            printWriter.close();
            fos.close();
            Log.d(TAG, "saveNote:\n" + note.toJSON());
            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        //jsonAsyn.execute();
        /*if (note != null) {
            title.setText(note.getTitle());
            text.setText(note.getText());
        }*/

        super.onResume();
    }

    private Note loadFile() {

        return null;
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
                //saveNote();
                startActivity(new Intent(EditActivity.this,MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public class JSONAsyn extends AsyncTask<Void, Void, Note> {


        @Override
        protected Note doInBackground(Void... voids) {

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
                String title = jsonObject.getString("title");
                String text = jsonObject.getString("text");
                return new Note(title,text);

            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(), getString(R.string.no_file), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
