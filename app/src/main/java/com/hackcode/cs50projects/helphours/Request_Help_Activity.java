package com.hackcode.cs50projects.helphours;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Request_Help_Activity extends AppCompatActivity {
    /*activity for students to make a new request for help*/

    //Website URL for volley to connect to python sever
    final String post_URL = "http://polar-reef-81647.herokuapp.com/sendData";

    //create volley request queue
    RequestQueue queue;

    //on start of actvity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //create activity instance and display
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request__help_);

        //construct volley request queue
        queue = Volley.newRequestQueue(this);

        //create EditTexts for students to input info
        final EditText nameEditText  = (EditText)findViewById(R.id.nameEditText);
        final EditText tableEditText  = (EditText)findViewById(R.id.tableEditText);
        final EditText problemEditText  = (EditText)findViewById(R.id.problemEditText);
        final EditText descriptionEditText  = (EditText)findViewById(R.id.descriptionEditText);

        //button for submitting request
        Button submitButton = (Button)findViewById(R.id.submitButton);

        //recognize when button clicked
        submitButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {

                        //get Strings inputted into EditText boxes
                        String nameString = nameEditText.getText().toString();
                        String tableString = tableEditText.getText().toString();
                        String problemString = problemEditText.getText().toString();
                        String descriptionString = descriptionEditText.getText().toString();

                        //if any of the required strings are empty, do not go through with request, send message to user
                        if (empty(nameString)){
                            Toast.makeText(view.getContext(), "You did not enter a username", Toast.LENGTH_SHORT).show();
                        }
                        else if (empty(tableString)){
                            Toast.makeText(view.getContext(), "You did not enter a table id", Toast.LENGTH_SHORT).show();
                        }
                        else if (empty(problemString)) {
                            Toast.makeText(view.getContext(), "You did not enter a problem", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //Description not required, set to none if empty
                            if (empty(descriptionString)){
                                descriptionString = "None";
                            }
                            try {
                                //send new request data to server
                                sendHelpRequest(nameString, tableString, problemString, descriptionString);
                            } catch (JSONException e) {
                                //print any errors
                                e.printStackTrace();
                            }
                        }

                        //go back to Main Activity
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_request__help_, menu);
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


    //method to check if a string is empty
    public boolean empty(String s){
        if (s.matches("")) {
            return true;
        }
        return false;
    }


    //sends new user request to database
    public void sendHelpRequest(final String name,final String table,final String problem,final String description) throws JSONException {

        //create JSON object to send to server
        JSONObject obj = new JSONObject();

        //input problem details to JSON object to send to server
        obj.put("student_name", name);
        obj.put("table_id", table);
        obj.put("problem", problem);
        obj.put("description", description);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, post_URL, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("YAY", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //print any volley errors
                Log.e("Volley Error", error.toString());
            }
        });
        queue.add(jsObjRequest);
    }


}
