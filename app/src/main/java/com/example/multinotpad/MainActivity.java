package com.example.multinotpad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


    /*
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
    }*/









}