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
    //defining preferences
    SharedPreferences myPrefs;
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
        //init preferences
        myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        //init nav view
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.page_1);

        outputData3 = myPrefs.getString(Constants.TEAM_NUMBER.toString(),outputData3);
        data3.setText(outputData3);

        // when you click different pages on bottom bar view it changes page
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.page_1) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
            else if (item.getItemId() == R.id.submit) {
                startActivity(new Intent(getApplicationContext(), SubmitActivity.class));
                finish();
            }
            return false;
        });

        // Updates variable (output2) when the text is changed
        data3.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                outputData3 = data3.getText().toString();
                editor.putString(Constants.TEAM_NUMBER.toString(), outputData3);
                editor.apply();
            } @Override public void afterTextChanged(Editable s) {} });

        // clears inputs
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(Constants.FIRST_NAME.toString(), null);
                editor.apply();

                editor.putString(Constants.LAST_NAME.toString(), null);
                editor.apply();

                outputData3 = null;
                data3.setText(outputData3);
                editor.putString(Constants.TEAM_NUMBER.toString(), null);
                editor.apply();
            }});
    }
}