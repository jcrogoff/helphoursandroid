package com.hackcode.cs50projects.helphours;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class TF_SeeInfo_Activity extends AppCompatActivity {
/* Activity to display more information about selected problem (from TF view)*/


    //url to connect to server and databases
    final String move_URL = "http://polar-reef-81647.herokuapp.com/moveData";

    //variables for volley request queue
    RequestQueue queue;
    List<String> items;

    //on start of actvity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //create activity instance and display
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tf_see_info);

        //construct volley request queue
        queue = Volley.newRequestQueue(this);

        //get info about problem sent from previous activity
        Bundle bundle = getIntent().getExtras();
        String clickedInfo = bundle.getString("clickedInfo");
        Log.e("Trying", clickedInfo);
        items = Arrays.asList(clickedInfo.split("\\s*,\\s*"));

        //display problem info in textViews on screen
        TextView tv = (TextView)findViewById(R.id.textView);
        tv.setText(items.get(1));
        TextView tv2 = (TextView)findViewById(R.id.textView2);
        tv2.setText(items.get(2));
        TextView tv3 = (TextView)findViewById(R.id.textView3);
        tv3.setText(items.get(3));
        TextView tv4 = (TextView)findViewById(R.id.textView4);
        tv4.setText(items.get(4));
        TextView tv6 = (TextView)findViewById(R.id.textView5);
        tv6.setText("Waiting " + items.get(6) + "min");

        //Help button, to respond to a TF filling a request
        Button helpButton = (Button)findViewById(R.id.helpRequestButton);
        //when button clicked
        helpButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                            //send "helprequst" to database, I.E. move from current_request database to filled_request database
                            try {
                                sendHelpRequest(items.get(0),items.get(1), items.get(2), items.get(3), items.get(4), items.get(5));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        //go back to the main activity
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

    }


    //send request to database (serverside, row with this info is moved into filled request)
    public void sendHelpRequest(final String id, final String name,final String table,final String problem,final String description, final String time_submitted) throws JSONException {
        //create JSON object to send to server
        JSONObject obj = new JSONObject();

        //input problem details to JSON object to send to server
        obj.put("request_id", id);
        obj.put("student_name", name);
        obj.put("table_id", table);
        obj.put("problem", problem);
        obj.put("description", description);
        obj.put("time_submitted", time_submitted);

        //attempt to post to server
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, move_URL, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("YAY", response.toString());
            }
        }, new Response.ErrorListener() {
            //print any errors
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
            }
        });
        //add volley request to queue
        queue.add(jsObjRequest);
    }
}
