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

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    Button clearButton;
    //defining strings
    String outputData1;
    String outputData2;
    //defining preferences
    SharedPreferences myPrefs;
    //define bottom nav
    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing edit text
        data1 = (EditText) findViewById(R.id.data1);
        data2 = (EditText) findViewById(R.id.data2);
        // init buttons
        clearButton = (Button) findViewById(R.id.clearTextButton);
        //init preferences
        myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        //init nav view
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.page_1);


        // setting data to save when reopening the page
        outputData1 = myPrefs.getString(Constants.FIRST_NAME.toString(),outputData1);
        data1.setText(outputData1);
        outputData2 = myPrefs.getString(Constants.LAST_NAME.toString(),outputData2);
        data2.setText(outputData2);

        // when you click different pages on bottom bar view it changes page
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.page_2) {
                    startActivity(new Intent(getApplicationContext(), SecondActivity.class));
                    finish();
            }
            else if (item.getItemId() == R.id.submit) {
                    startActivity(new Intent(getApplicationContext(), SubmitActivity.class));
                    finish();
            }
            return false;
        });


        // Updates variable (output1) when the text is changed
        data1.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                outputData1 = data1.getText().toString();
                editor.putString(Constants.FIRST_NAME.toString(), outputData1);
                editor.apply();
            } @Override public void afterTextChanged(Editable s) {} });

        // Updates variable (output2) when the text is changed
        data2.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                outputData2 = data2.getText().toString();
                editor.putString(Constants.LAST_NAME.toString(), outputData2);
                editor.apply();
            } @Override public void afterTextChanged(Editable s) {} });

        //clears inputs
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputData1 = null;
                data1.setText(outputData1);
                editor.putString(Constants.FIRST_NAME.toString(), null);
                editor.apply();

                outputData2 = null;
                data2.setText(outputData2);
                editor.putString(Constants.LAST_NAME.toString(), null);
                editor.apply();

                editor.putString(Constants.TEAM_NUMBER.toString(), null);
                editor.apply();
            }
        });

    }
}