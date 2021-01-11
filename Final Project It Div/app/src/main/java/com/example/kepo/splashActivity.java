package com.example.kepo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.kepo.model.User;

public class splashActivity extends AppCompatActivity {

    private Handler waitingHandler = new Handler();
    private UserLog userLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        waitingHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    userLog = new UserLog(splashActivity.this);
                    User user = userLog.loadLog();

                    Intent getIntent;
                    if(user.getUser_id().equals("")){
                        getIntent = new Intent(splashActivity.this, loginActivity.class);
                    }else{
                        getIntent = new Intent(splashActivity.this, HomeActivity.class);
                    }
                    startActivity(getIntent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, 1500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        waitingHandler.removeCallbacksAndMessages(null);
    }
}