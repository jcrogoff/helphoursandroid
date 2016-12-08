package com.hackcode.cs50projects.helphours;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
/* Main Activity, has buttons to get to different views*/

    //on start of actvity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //create activity instance and display
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button to open request help activity when clicked
        final Button helpRequestButton = (Button) findViewById(R.id.helpRequestButton);
        helpRequestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //start request help activity
                Intent intent = new Intent(v.getContext(), Request_Help_Activity.class);
                startActivity(intent);
            }
        });

        //button to open student see requests activity when clicked
        final Button studentLookButton = (Button) findViewById(R.id.studentLookButton);
        studentLookButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //Start student see requests activity
                Intent intent = new Intent(v.getContext(), Student_Look_Requests_Activity.class);
                startActivity(intent);
            }
        });

        //button to open TF see requests activity when clicked
        final Button tfLookButton = (Button) findViewById(R.id.tfLookButton);
        tfLookButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //Start student see requests activity
                Intent intent = new Intent(v.getContext(), TF_Look_Requests_Activity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
