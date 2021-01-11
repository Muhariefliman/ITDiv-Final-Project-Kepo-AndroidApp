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
import com.example.kepo.databinding.ActivitySearchUserBinding;
import com.example.kepo.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchUserActivity extends AppCompatActivity {

    private ActivitySearchUserBinding binding;
    private static final String BASE_URL = "https://it-division-kepo.herokuapp.com/searchUser";
    private UserAdapter userAdapter;
    private UserLog userLog;
    private User nowUser;
    private String name_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SearchUserActivity.this, R.layout.activity_search_user);
        userLog = new UserLog(this);
        nowUser = userLog.loadLog();

        binding.ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_search = binding.etUsername.getText().toString();
                binding.tvResFor.setVisibility(View.VISIBLE);
                binding.tvUsername.setText('"'+name_search+'"');
                binding.tvNodata.setVisibility(View.INVISIBLE);
                initAdapter();
                loadData();
            }
        });

    }


    private void initAdapter(){
        userAdapter = new UserAdapter(this);
        binding.rvUser.setLayoutManager(new LinearLayoutManager(this));
        binding.rvUser.setAdapter(userAdapter);
    }

    private void loadData(){
        binding.pbLoad.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL,
                reqBodyUser(nowUser.getUser_id(), name_search),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ArrayList<User> listOfUser = new ArrayList<>();
                            JSONArray data = response.getJSONArray("data");

                            for (int i = 0; i < data.length(); i++){
                                JSONObject userData = data.getJSONObject(i);

                                User user = new User();

                                user.setUser_id(userData.getString("user_id"));
                                user.setUsername(userData.getString("username"));
                                user.setName(userData.getString("name"));

                                listOfUser.add(user);
                            }

                            userAdapter.updateData(listOfUser);
                            if (data.length() == 0){
                                binding.tvNodata.setVisibility(View.VISIBLE);
                            }
                            binding.pbLoad.setVisibility(View.INVISIBLE);


                        }catch (Exception e){
                            binding.pbLoad.setVisibility(View.INVISIBLE);
                            Toast.makeText(SearchUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        binding.pbLoad.setVisibility(View.INVISIBLE);
                        Toast.makeText(SearchUserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);

    }

    private JSONObject reqBodyUser(String user_id, String name_search){
        JSONObject newBody = new JSONObject();

        try{
            newBody.put("user_id", user_id);
            newBody.put("searchQuery", name_search);
            return  newBody;
        }catch (Exception e){
            return null;
        }
    }

}