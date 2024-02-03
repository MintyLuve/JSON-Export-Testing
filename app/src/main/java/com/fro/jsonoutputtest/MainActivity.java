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
        //init nav view
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.page_1);


        // setting data to save when reopening the page
        data1.setText(testConstants.uno);
        data2.setText(testConstants.dos);

        // when you click different pages on bottom bar view it changes page
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.page_2) {
                    startActivity(new Intent(getApplicationContext(), SecondActivity.class));
                    finish();}
            else if (item.getItemId() == R.id.submit) {
                    startActivity(new Intent(getApplicationContext(), SubmitActivity.class));
                    finish();}
            return false;});

        // Updates variable (output1) when the text is changed
        data1.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                testConstants.uno = data1.getText().toString();
            } @Override public void afterTextChanged(Editable s) {} });

        // Updates variable (output2) when the text is changed
        data2.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                testConstants.dos = data2.getText().toString();
            } @Override public void afterTextChanged(Editable s) {} });

        //clears inputs
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data1.setText(null);
                testConstants.uno = null;

                data2.setText(null);
                testConstants.dos = null;

                testConstants.tres = null;
        }});
    }
}