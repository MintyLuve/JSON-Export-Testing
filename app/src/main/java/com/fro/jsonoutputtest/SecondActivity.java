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

public class SecondActivity extends AppCompatActivity {

    // defining edit text
    EditText data3;
    //defining buttons
    Button backButton;
    Button clearButton;
    //defining strings
    String outputData3;
    //defining preferences
    SharedPreferences myPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //initializing edit text
        data3 = (EditText) findViewById(R.id.data3);
        // init buttons
        clearButton = (Button) findViewById(R.id.clearTextButton);
        backButton = (Button) findViewById(R.id.backButton);
        //init preferences
        myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();

        outputData3 = myPrefs.getString("3",outputData3);
        data3.setText(outputData3);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // Updates variable (output2) when the text is changed
        data3.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                outputData3 = data3.getText().toString();
                editor.putString("3", outputData3);
                editor.apply();
            } @Override public void afterTextChanged(Editable s) {} });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("1", null);
                editor.apply();

                editor.putString("2", null);
                editor.apply();

                outputData3 = null;
                data3.setText(outputData3);
                editor.putString("3", null);
                editor.apply();
            }});
    }
}