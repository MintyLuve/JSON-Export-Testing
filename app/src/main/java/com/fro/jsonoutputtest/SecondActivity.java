package com.fro.jsonoutputtest;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SecondActivity extends AppCompatActivity {

    // defining edit text
    EditText data3;
    //defining buttons
    Button clearButton;
    //defining strings
    String outputData3;
    //define bottom nav
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //initializing edit text
        data3 = (EditText) findViewById(R.id.data3);
        // init buttons
        clearButton = (Button) findViewById(R.id.clearTextButton);
        //init nav view
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.page_1);

        data3.setText(testConstants.tres);

        // when you click different pages on bottom bar view it changes page
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.page_1) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();}
            else if (item.getItemId() == R.id.submit) {
                startActivity(new Intent(getApplicationContext(), SubmitActivity.class));
                finish();}
            return false;});

        // Updates variable (output2) when the text is changed
        data3.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                outputData3 = data3.getText().toString();
                testConstants.tres = outputData3;
            } @Override public void afterTextChanged(Editable s) {} });

        //clears inputs
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testConstants.uno = null;
                testConstants.dos = null;

                outputData3 = null;
                data3.setText(outputData3);
                testConstants.tres = outputData3;
            }});
    }
}