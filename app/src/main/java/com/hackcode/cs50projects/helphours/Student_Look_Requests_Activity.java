package com.hackcode.cs50projects.helphours;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Student_Look_Requests_Activity extends AppCompatActivity {
    /*activity for students to view current requests and wait times*/

    //Website URL for volley to connect to python sever
    final String get_URL = "http://polar-reef-81647.herokuapp.com/getData";

    //create volley request queue
    RequestQueue queue;

    //varibles for volley request
    String problem, timestamp;

    //variables to display text in list view
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> requestsToDisplay = new ArrayList<String>();
    android.widget.ListView listView;

    //on start of actvity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //create activity instance and display
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__look__requests_);

        //create queue to make request to server database
        queue = Volley.newRequestQueue(this);

        //see get DB methon (gets info from server database)
        getDB();

        //fill listView with database info
        listView = (ListView)findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.custom_lv, requestsToDisplay);
        listView.setClickable(false);
        listView.setAdapter(arrayAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student__look__requests_, menu);
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

    //gets Info from database
    public void getDB() {
        JsonArrayRequest request = new JsonArrayRequest(get_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //this gets jsonarray from database, then use to display in java
                        try {
                            //format JSON response for program needs
                            displayRequestsFromJson(response);
                        }//print any errors if found
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley_error", error.toString());
            }
        });
        //add the "request" to the request queue
        queue.add(request);
    }


    //method to iterate through JSON array and save objects
    public void displayRequestsFromJson(JSONArray json) throws ParseException {

        //varibles for current timestamp
        Date jsonTime, currentTime;
        DateFormat df = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        //iterate through json array recieved from server
        for(int i=0; i<json.length(); i++)
        {
            try {
                //current Json object in array recieved from server
                JSONObject cur = json.getJSONObject(i);

                //save problem and timestamp from json array recieved from server
                problem = cur.getString("problem");
                timestamp = cur.getString("time_submitted");

                //calculate waittime (I could not pull this out into a method)
                jsonTime = df.parse(timestamp);
                String date = df.format(Calendar.getInstance().getTime());
                currentTime = df.parse(date);
                long diff = currentTime.getTime() - jsonTime.getTime();
                long seconds = diff / 1000;
                long minutes = seconds / 60;
                String difference = Long.toString(minutes);

                //add info to listView display
                requestsToDisplay.add("Problem: " + problem + "  " + difference + "min");
            }
            //print any errors if found
            catch (JSONException e)
            {
                Log.e("json", e.toString());
            }

        }
    }

}
