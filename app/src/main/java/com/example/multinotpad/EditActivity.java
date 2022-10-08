package com.example.multinotpad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    Note note;
    private EditText title;
    private EditText text;
    //JSONAsyn jsonAsyn;
    ArrayList<JSONObject> list = new ArrayList<>();
    JSONArray jsArray;   // array for fetchin Note Array from Note.JSON
    JSONObject mainObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        title=findViewById(R.id.etTitleNote);
        text =findViewById(R.id.etTextNote);
        mainObject =  new JSONObject();
    }

        @Override
    protected void onPause() {
        String titleStr = title.getText().toString();
        String textStr = text.getText().toString();
        if(!titleStr.isEmpty() && !textStr.isEmpty()){
            note = new Note(titleStr,textStr);
        }
        saveNote();   //JSon Data Saving function
        super.onPause();
    }
    private void saveNote(){
        Log.d(TAG, "saveNote: Saving JSON File");
        try{
            FileOutputStream fos = getApplicationContext().openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);
            PrintWriter printWriter = new PrintWriter(fos);
            JSONObject noteObject = new JSONObject();
            noteObject.put("title",note.getTitle());
            noteObject.put("text",note.getText());

            jsArray = new JSONArray();
            jsArray.put(noteObject);
            mainObject.put("Notes",jsArray);
            printWriter.write(mainObject.toString());
            printWriter.flush();
            fos.close();


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
                Log.d("TTTTTT",array.get(i).toString());
                String title = array.getJSONObject(i).getString("title");
                String text= array.getJSONObject(i).getString("text");
                Log.d("TTTT","title: "+title + " text " + text);
            }
            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        //jsonAsyn.execute();
        super.onResume();
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


}
