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
import com.example.kepo.databinding.ActivityUpdateTodoBinding;
import com.example.kepo.model.Todo;

import org.json.JSONObject;

public class UpdateTodoActivity extends AppCompatActivity {

    private ActivityUpdateTodoBinding binding;
    private static final String OBJ_USER = "OBJ_USER";
    private Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(UpdateTodoActivity.this, R.layout.activity_update_todo);

        todo = (Todo)getIntent().getSerializableExtra(OBJ_USER);
        binding.etDesc.setText(todo.getDesc());
        binding.etTitle.setText(todo.getTitle());
        binding.tvNumCount.setText(String.valueOf(todo.getDesc().length()));

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

        binding.ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(UpdateTodoActivity.this, DetailTodoActivity.class);
                getIntent.putExtra("OBJ_TODO", todo);
                startActivity(getIntent);
                finish();
            }
        });


        binding.ivCheckTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEtField()){
                    AlertDialog.Builder logoutDialog = new AlertDialog.Builder(UpdateTodoActivity.this);

                    logoutDialog.setTitle("Update Todo");
                    logoutDialog.setMessage("Are You Sure Want To Update?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    updateTodo();
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
                    Toast.makeText(UpdateTodoActivity.this, "Title or Description Can't Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validateEtField(){
        if(!binding.etTitle.getText().equals("") && !binding.etDesc.getText().equals("")) return true;
        else return false;
    }

    private void updateTodo(){
        String BASE_URL = "https://it-division-kepo.herokuapp.com/user/"+todo.getUser_id()+"/todo/"+todo.getTodo_id();
        binding.pbLoad.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                BASE_URL,
                reqUpdateBody(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        binding.pbLoad.setVisibility(View.INVISIBLE);
                        try{
                            Toast.makeText(UpdateTodoActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent getIntent = new Intent(UpdateTodoActivity.this, DetailTodoActivity.class);
                            getIntent.putExtra("OBJ_TODO", todo);
                            startActivity(getIntent);
                            finish();
                        }catch (Exception e){
                            Toast.makeText(UpdateTodoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        binding.pbLoad.setVisibility(View.INVISIBLE);
                        Toast.makeText(UpdateTodoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(UpdateTodoActivity.this);
        queue.add(jsonObjectRequest);

    }


    private JSONObject reqUpdateBody(){
        try {
            JSONObject body = new JSONObject();

            body.put("title", binding.etTitle.getText());
            body.put("description", binding.etDesc.getText());

            return body;


        }catch (Exception e){
            return null;
        }
    }



}