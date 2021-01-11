package com.example.kepo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.kepo.databinding.ActivityDetailTodoBinding;
import com.example.kepo.model.Todo;
import com.example.kepo.model.User;

import org.json.JSONObject;

public class DetailTodoActivity extends AppCompatActivity {

    private ActivityDetailTodoBinding binding;
    private final  static  String OBJ_TODO = "OBJ_TODO";
    private UserLog userLog;
    private User user;
    private Todo nowTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(DetailTodoActivity.this, R.layout.activity_detail_todo);

        nowTodo = (Todo) getIntent().getSerializableExtra(OBJ_TODO);
        userLog = new UserLog(this);
        user = userLog.loadLog();


        if(nowTodo.getUser_id().equals(user.getUser_id())){
            binding.ivEditTodo.setVisibility(View.VISIBLE);
            binding.ivEditTodo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gotIntent = new Intent(DetailTodoActivity.this, UpdateTodoActivity.class);
                    gotIntent.putExtra("OBJ_USER", nowTodo);
                    startActivity(gotIntent);
                    finish();
                }
            });
        }






        binding.ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadData();
    }

    private void loadData(){
        binding.pbLoad.setVisibility(View.VISIBLE);
        String BASE_URL = "https://it-division-kepo.herokuapp.com/user/"+nowTodo.getUser_id()+"/todo/"+nowTodo.getTodo_id();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                BASE_URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            binding.pbLoad.setVisibility(View.INVISIBLE);
                            JSONObject data = response.getJSONObject("data");

                            String LastEdited = data.getString("last_edited");
                            String desc = data.getString("description");
                            nowTodo.setLastEdited(LastEdited);
                            nowTodo.setDesc(desc);
                            binding.tvDate.setText(nowTodo.getLastEdited());
                            binding.tvDesc.setText(desc);
                            binding.tvTitle.setText(nowTodo.getTitle());


                        }catch (Exception e){
                            binding.pbLoad.setVisibility(View.INVISIBLE);
                            Toast.makeText(DetailTodoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        binding.pbLoad.setVisibility(View.INVISIBLE);
                        Toast.makeText(DetailTodoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }




}