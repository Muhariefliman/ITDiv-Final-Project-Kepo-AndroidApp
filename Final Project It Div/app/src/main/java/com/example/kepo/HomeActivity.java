package com.example.kepo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kepo.databinding.ActivityHomeBinding;
import com.example.kepo.model.User;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private UserLog userlog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        userlog = new UserLog(this);
        User user = userlog.loadLog();

        binding.tvName.setText(user.getName());

        // My Todo Action
        binding.llMyTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(HomeActivity.this, MyTodoActivity.class);
                startActivity(getIntent);

            }
        });

        // Search Todo action
        binding.llSearchTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Search Person Action
        binding.llSearchPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(HomeActivity.this, SearchUserActivity.class);
                startActivity(getIntent);
            }
        });


        // Profile Action
        binding.llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(getIntent);
            }
        });




    }

}