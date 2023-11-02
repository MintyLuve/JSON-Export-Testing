package com.fro.jsonoutputtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
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

public class MainActivity extends AppCompatActivity {

    // defining variables
    EditText data1;
    String outputData1;
    EditText data2;
    String outputData2;
    Button submitButton;
    Button yesButton;
    Button noButton;
    TextView confirmation;

    public void toJSON(JSONObject content) throws IOException {
        // Class to put the data into a JSON object
        File path = getApplicationContext().getFilesDir();
        Toast.makeText(MainActivity.this, path.toString(), Toast.LENGTH_SHORT).show();
        FileOutputStream writer = new FileOutputStream(new File(path, "JSON_Text.txt"));
        writer.write(content.toString().getBytes());
        writer.close();
    }
    //JSON class
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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesButton.setVisibility(View.VISIBLE);
                noButton.setVisibility(View.VISIBLE);
                confirmation.setVisibility(View.VISIBLE);
                if (confirmation.getText() != "Send to Database?") {
                    confirmation.setText("Send to Database?");
                }

                outputData1 = data1.getText().toString();
                outputData2 = data2.getText().toString();

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
        });

    }

}