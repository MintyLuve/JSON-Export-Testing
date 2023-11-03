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

    // defining edit text
    EditText data1;
    EditText data2;
    //defining buttons
    Button submitButton;
    Button yesButton;
    Button noButton;
    Button nextPageButton;
    Button clearButton;
    //defining strings
    String outputData1;
    String outputData2;
    TextView confirmation;
    //defining preferences
    SharedPreferences myPrefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing edit text
        data1 = (EditText) findViewById(R.id.data1);
        data2 = (EditText) findViewById(R.id.data2);
        // init buttons
        submitButton = (Button) findViewById(R.id.submitButton);
        yesButton = (Button) findViewById(R.id.yesButton);
        noButton = (Button) findViewById(R.id.noButton);
        nextPageButton = (Button) findViewById(R.id.nextPageButton);
        clearButton = (Button) findViewById(R.id.clearTextButton);
        //init textview
        confirmation = (TextView) findViewById(R.id.confirmation);
        //init preferences
        myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();

        // setting data to save when reopening the page
        outputData1 = myPrefs.getString("1",outputData1);
        data1.setText(outputData1);
        outputData2 = myPrefs.getString("2",outputData2);
        data2.setText(outputData2);


        // switches activities
        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            } });

        // Updates variable (output1) when the text is changed
        data1.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                outputData1 = data1.getText().toString();
                editor.putString("1", outputData1);
                editor.apply();
                confirmation.setText(outputData1);
            } @Override public void afterTextChanged(Editable s) {} });

        // Updates variable (output2) when the text is changed
        data2.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                outputData2 = data2.getText().toString();
                editor.putString("2", outputData2);
                editor.apply();
                confirmation.setText(outputData2);
            } @Override public void afterTextChanged(Editable s) {} });

        //clears inputs
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputData1 = null;
                data1.setText(outputData1);
                editor.putString("1", null);
                editor.apply();

                outputData2 = null;
                data2.setText(outputData2);
                editor.putString("1", null);
                editor.apply();

                editor.putString("3", null);
                editor.apply();

                confirmation.setText("Send to Database?");
                yesButton.setVisibility(View.INVISIBLE);
                noButton.setVisibility(View.INVISIBLE);
                confirmation.setVisibility(View.INVISIBLE);
            }
        });

        // when the submit button is clicked it makes options visible
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesButton.setVisibility(View.VISIBLE);
                noButton.setVisibility(View.VISIBLE);
                confirmation.setVisibility(View.VISIBLE);
                if (confirmation.getText() != "Send to Database?") {
                    confirmation.setText("Send to Database?");
                }

                //If yes button is clicked, sets the buttons invisible, and outputs the data into a JSON
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yesButton.setVisibility(View.INVISIBLE);
                        noButton.setVisibility(View.INVISIBLE);
                        JSONObject jsonObject = new JSONObject();

                        try {jsonObject.put("OUTPUT_DATA_1", myPrefs.getString("1",null));}
                        catch (JSONException e) {throw new RuntimeException(e);}

                        try {jsonObject.put("OUTPUT_DATA_2", myPrefs.getString("2",null));}
                        catch (JSONException e) {throw new RuntimeException(e);}

                        try {jsonObject.put("OUTPUT_DATA_3", myPrefs.getString("3",null));}
                        catch (JSONException e) {throw new RuntimeException(e);}

                        try {toJSON(jsonObject);} catch (IOException e) {e.printStackTrace();}

                        confirmation.setText(outputData1+", "+outputData2);
                    }
                });

                //If the no button is clicked it hides the buttons
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yesButton.setVisibility(View.INVISIBLE);
                        noButton.setVisibility(View.INVISIBLE);
                        confirmation.setVisibility(View.INVISIBLE);
                    }
                });


            }

            // sends data to a json
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