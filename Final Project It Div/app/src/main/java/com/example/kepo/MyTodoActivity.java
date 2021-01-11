package com.example.kepo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.kepo.databinding.ActivityMyTodoBinding;
import com.example.kepo.model.Todo;
import com.example.kepo.model.User;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyTodoActivity extends AppCompatActivity {

    private ActivityMyTodoBinding binding;
    private MyTodoAdapter myTodoAdapter;
    private UserLog userLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MyTodoActivity.this, R.layout.activity_my_todo);


        userLog = new UserLog(this);
        User user = userLog.loadLog();

        binding.ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.ivAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(MyTodoActivity.this, CreateTodoActivity.class);
                startActivity(getIntent);
                finish();
            }
        });

        initTodoAdapter();
        loadTodo(user);
    }
    private void initTodoAdapter(){
        myTodoAdapter = new MyTodoAdapter(this);
        binding.rvTodos.setLayoutManager(new LinearLayoutManager(this));
        binding.rvTodos.setAdapter(myTodoAdapter);

    }

    private void loadTodo(User user){
        binding.pbLoad.setVisibility(View.VISIBLE);
        String BASE_URL = "https://it-division-kepo.herokuapp.com/user/"+user.getUser_id()+"/todo";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                BASE_URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        binding.pbLoad.setVisibility(View.INVISIBLE);
                        try {
                            ArrayList<Todo> listTodo = new ArrayList<Todo>();
                            JSONObject data = response.getJSONObject("data");
                            JSONArray listData = data.getJSONArray("listTodo");

                            for (int i = 0; i < listData.length(); i++){
                                JSONObject todo = listData.getJSONObject(i);
                                Todo newTodo = new Todo();

                                newTodo.setTodo_id(todo.getString("todo_id"));
                                newTodo.setTitle(todo.getString("title"));
                                newTodo.setLastEdited(todo.getString("last_edited"));
                                newTodo.setUser_id(data.getString("userId"));
                                newTodo.setName(data.getString("name"));

                                listTodo.add(newTodo);

                            }

                            if(listData.length() == 0){
                                binding.tvNodata.setVisibility(View.VISIBLE);
                            }else{
                                myTodoAdapter.updateData(listTodo);
                                user.setMyTodo(listTodo);
                            }


                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }





}