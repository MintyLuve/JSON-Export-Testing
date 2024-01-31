package com.fro.jsonoutputtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class SubmitActivity extends AppCompatActivity {
    //defining buttons
    Button submitButton;
    Button yesButton;
    Button noButton;
    //defining text view
    TextView confirmation;
    //defining preferences
    SharedPreferences myPrefs;
    //define bottom nav
    BottomNavigationView bottomNavigationView;
    //define calander
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        // init buttons
        submitButton = (Button) findViewById(R.id.submitButton);
        yesButton = (Button) findViewById(R.id.yesButton);
        noButton = (Button) findViewById(R.id.noButton);
        //init textview
        confirmation = (TextView) findViewById(R.id.confirmation);
        //init prefs
        myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        //init nav view
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.page_1);
        // init calendar
        calendar = Calendar.getInstance();

        // when you click different pages on bottom bar view it changes page
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.page_1) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
            else if (item.getItemId() == R.id.page_2) {
                startActivity(new Intent(getApplicationContext(), SecondActivity.class));
                finish();
            }
            return false;
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesButton.setVisibility(View.VISIBLE);
                noButton.setVisibility(View.VISIBLE);
                confirmation.setVisibility(View.VISIBLE);
                String sendTo = "Send to Database?";
                if (!confirmation.getText().equals(sendTo)) {
                    confirmation.setText(sendTo);
                }

                //If yes button is clicked, sets the buttons invisible, and outputs the data into a JSON
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yesButton.setVisibility(View.INVISIBLE);
                        noButton.setVisibility(View.INVISIBLE);
                        JSONObject jsonObject = new JSONObject();

                        try {jsonObject.put("OUTPUT_DATA_1", myPrefs.getString(Constants.FIRST_NAME.toString(),null));}
                        catch (JSONException e) {throw new RuntimeException(e);}

                        try {jsonObject.put("OUTPUT_DATA_2", myPrefs.getString(Constants.LAST_NAME.toString(),null));}
                        catch (JSONException e) {throw new RuntimeException(e);}

                        try {jsonObject.put("OUTPUT_DATA_3", myPrefs.getString(Constants.TEAM_NUMBER.toString(),null));}
                        catch (JSONException e) {throw new RuntimeException(e);}

                        try {toJSON(jsonObject);} catch (IOException e) {e.printStackTrace();}

                        String output = myPrefs.getString(Constants.FIRST_NAME.toString(), null)
                                + ", " + myPrefs.getString(Constants.LAST_NAME.toString(), null)
                                + ", " + myPrefs.getString(Constants.TEAM_NUMBER.toString(), null);
                        confirmation.setText(output);
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
                Toast.makeText(SubmitActivity.this, path.toString(), Toast.LENGTH_SHORT).show();
                FileOutputStream writer = new FileOutputStream(new File(path, "JSON_Text_" + calendar.getTimeInMillis() + ".txt"));
                writer.write(content.toString().getBytes());
                writer.close();
            }
        });

    }
}