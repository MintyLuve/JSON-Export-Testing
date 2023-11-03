package com.fro.jsonoutputtest;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


//Car export test
public class MainActivity extends AppCompatActivity {

    // defining variables
    EditText data1;
    String outputData1 = "Input";
    EditText data2;
    String outputData2;
    Button submitButton;
    Button yesButton;
    Button noButton;
    TextView confirmation;
    Button nextPageButton;
    SharedPreferences myPrefs;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing variables
        submitButton = (Button) findViewById(R.id.submitButton);
        data1 = (EditText) findViewById(R.id.data1);
        data2 = (EditText) findViewById(R.id.data2);
        yesButton = (Button) findViewById(R.id.yesButton);
        noButton = (Button) findViewById(R.id.noButton);
        confirmation = (TextView) findViewById(R.id.confirmation);
        nextPageButton = (Button) findViewById(R.id.nextPageButton);
        myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();

        outputData1 = myPrefs.getString("1",outputData1);
        data1.setText(outputData1);
        outputData2 = myPrefs.getString("2",outputData2);
        data1.setText(outputData2);



        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        data1.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                outputData1 = data1.getText().toString();
                editor.putString("1", outputData1);
                editor.apply();
                confirmation.setText(outputData1);
            } @Override public void afterTextChanged(Editable s) {} });
        data2.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                outputData2 = data2.getText().toString();
                editor.putString("2", outputData2);
                editor.apply();
                confirmation.setText(outputData2);
            } @Override public void afterTextChanged(Editable s) {} });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesButton.setVisibility(View.VISIBLE);
                noButton.setVisibility(View.VISIBLE);
                confirmation.setVisibility(View.VISIBLE);
                if (confirmation.getText() != "Send to Database?") {
                    confirmation.setText("Send to Database?");
                }

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yesButton.setVisibility(View.INVISIBLE);
                        noButton.setVisibility(View.INVISIBLE);
                        JSONObject jsonObject = new JSONObject();

                        try {jsonObject.put("OUTPUT_DATA_1", outputData1);}
                        catch (JSONException e) {throw new RuntimeException(e);}

                        try {jsonObject.put("OUTPUT_DATA_2", outputData2);}
                        catch (JSONException e) {throw new RuntimeException(e);}

                        try {toJSON(jsonObject);} catch (IOException e) {e.printStackTrace();}

                        confirmation.setText(outputData1+", "+outputData2);
                    }
                });

                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yesButton.setVisibility(View.INVISIBLE);
                        noButton.setVisibility(View.INVISIBLE);
                        confirmation.setVisibility(View.INVISIBLE);
                    }
                });



            }

            public void toJSON(JSONObject content) throws IOException {
                // Class to put the data into a JSON object
                File path = getApplicationContext().getFilesDir();
                Toast.makeText(MainActivity.this, path.toString(), Toast.LENGTH_SHORT).show();
                FileOutputStream writer = new FileOutputStream(new File(path, "JSON_Text.txt"));
                writer.write(content.toString().getBytes());
                writer.close();
            }
        });

    }

}