package com.example.kepo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.kepo.databinding.ActivityLoginBinding;
import com.example.kepo.model.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class loginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private static final String BASE_URL = "https://it-division-kepo.herokuapp.com/login";
    private UserLog userlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        userlog = new UserLog(this);
        User user = userlog.loadLog();

        if(!user.getUser_id().equals("")){
            gotoHomeAct();
        }

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginPost();
            }
        });

    }

    private void LoginPost(){
        User user = new User();
        user.setUsername(binding.etUsername.getText().toString());
        user.setPassword(binding.etPassword.getText().toString());
        if(user.getUsername().equals("") || user.getPassword().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity.this);

            builder.setTitle("Error")
                    .setMessage("Please Input Username or Password")
                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            binding.etPassword.setText("");
                            binding.etUsername.setText("");
                            dialog.cancel();
                        }
                    });

            AlertDialog ad = builder.create();
            ad.getWindow().setGravity(Gravity.BOTTOM);
            ad.show();
        }else{
            binding.pbReqMeth.setVisibility(View.VISIBLE);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    BASE_URL,
                    getBodyJson(user),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            binding.pbReqMeth.setVisibility(View.GONE);
                            try{
                                int status = response.getInt("status");
                                String msg = response.getString("message");
                                JSONObject data = response.getJSONObject("data");
                                if(status == 200 && msg.equals("Login success")){
                                    String user_id = data.getString("user_id");
                                    String name = data.getString("name");

                                    user.setUser_id(user_id);
                                    user.setName(name);

                                    userlog.saveLog(user);

                                    gotoHomeAct();
                                }else{

                                }
                            }catch (Exception e){
                                String msg = null;
                                int status = 0;
                                try {
                                    status = response.getInt("status");
                                    msg = response.getString("message");
                                } catch (JSONException jsonException) {
                                    jsonException.printStackTrace();
                                }

                                AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity.this);

                                builder.setTitle("Error")
                                        .setMessage(msg)
                                        .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                binding.etPassword.setText("");
                                                binding.etUsername.setText("");
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog ad = builder.create();
                                ad.getWindow().setGravity(Gravity.BOTTOM);
                                ad.show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity.this);

                            builder.setTitle("Error")
                                    .setMessage(error.toString())
                                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            binding.etPassword.setText("");
                                            binding.etUsername.setText("");
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog ad = builder.create();
                            ad.getWindow().setGravity(Gravity.BOTTOM);
                            ad.show();
                        }
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObjectRequest);
        }
    }

    private JSONObject getBodyJson(User user) {
        try {
            JSONObject nowBody = new JSONObject();
            nowBody.put("username", user.getUsername());
            nowBody.put("password", user.getPassword());
            return nowBody;
        }catch (Exception e){
            return null;
        }
    }

    private void gotoHomeAct(){
        Intent goIntent = new Intent(loginActivity.this, HomeActivity.class);
        startActivity(goIntent);finish();
    }








}