package com.hackcode.cs50projects.helphours;

/**
 * Created by jcrogoff on 12/6/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity {
    /*splash screen or "loading screen", displays at beinning of app when loading*/


    //on start of actvity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //create activity instance and display
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        //make sure loading screen displays for at least 3 seconds
        // (no functionality but it took me an hour to make and I wanted to make sure you saw it :p
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}