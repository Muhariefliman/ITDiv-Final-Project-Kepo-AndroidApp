package com.example.kepo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.kepo.databinding.ActivityCreateTodoBinding;
import com.example.kepo.model.User;

import org.json.JSONObject;

public class CreateTodoActivity extends AppCompatActivity {

    private ActivityCreateTodoBinding binding;
    private UserLog userLog;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(CreateTodoActivity.this, R.layout.activity_create_todo);

        userLog = new UserLog(this);
        user = userLog.loadLog();

        binding.ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.etDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                binding.tvNumCount.setText(String.valueOf(s.length()));

                if(s.length() == 100){
                    binding.tvErrorMsg.setText("Your description exceeded the maximum words");
                }
            }
        });

        binding.ivCheckTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEtField()){
                    AlertDialog.Builder logoutDialog = new AlertDialog.Builder(CreateTodoActivity.this);

                    logoutDialog.setTitle("Create ToDo");
                    logoutDialog.setMessage("Are You Sure Want To Add?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    createTodo();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = logoutDialog.create();
                    alertDialog.show();
                }else{
                    binding.tvErrorMsg.setText("Text Field Can't Empty");
                }
            }
        });


    }

    public boolean validateEtField(){
        if(!binding.etTitle.getText().equals("") && !binding.etDesc.getText().equals("")) return true;
        else return false;
    }

    private void createTodo(){
        binding.pbLoad.setVisibility(View.VISIBLE);
        String BASE_URL = "https://it-division-kepo.herokuapp.com/user/"+user.getUser_id()+"/todo";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL,
                getBodyTodo(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        binding.pbLoad.setVisibility(View.INVISIBLE);
                        gotoMyTodo();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        binding.pbLoad.setVisibility(View.INVISIBLE);
                        Toast.makeText(CreateTodoActivity.this, error.getMessage()+"\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }


    private JSONObject getBodyTodo(){
        JSONObject body = new JSONObject();
        try {
            body.put("title", binding.etTitle.getText());
            body.put("description", binding.etDesc.getText());
            return body;
        }catch (Exception e){
            return null;
        }
    }

    private void gotoMyTodo(){
        Intent getIntent = new Intent(CreateTodoActivity.this, MyTodoActivity.class);
        startActivity(getIntent);
        finish();
    }

}