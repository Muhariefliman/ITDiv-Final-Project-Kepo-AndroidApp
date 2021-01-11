package com.example.kepo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.kepo.databinding.ActivitySelectedUserBinding;
import com.example.kepo.model.Todo;
import com.example.kepo.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class selectedUserActivity extends AppCompatActivity {

    private ActivitySelectedUserBinding binding;
    private final static String OBJ_USER = "OBJ_USER";
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(selectedUserActivity.this, R.layout.activity_selected_user);

        User user = (User) getIntent().getSerializableExtra(OBJ_USER);

        binding.tvUsername.setText(user.getUsername());
        binding.tvName.setText(user.getName());

        initTodoAdapter();
        loadTodo(user);

        binding.ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void initTodoAdapter(){
        todoAdapter = new TodoAdapter(this);
        binding.rvTodos.setLayoutManager(new LinearLayoutManager(this));
        binding.rvTodos.setAdapter(todoAdapter);
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
                            todoAdapter.updateData(listTodo);

                            if(listData.length() == 0){
                                binding.tvNodata.setVisibility(View.VISIBLE);
                                binding.tvTotalTodos.setText("0");
                            }else{
                                user.setMyTodo(listTodo);
                                binding.tvTotalTodos.setText(String.valueOf(todoAdapter.getItemCount()));
                            }





                        }catch (Exception e){
                            binding.pbLoad.setVisibility(View.INVISIBLE);
                            Toast.makeText(selectedUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        binding.pbLoad.setVisibility(View.INVISIBLE);
                        Toast.makeText(selectedUserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }



}