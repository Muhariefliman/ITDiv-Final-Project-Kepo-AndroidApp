  package com.example.kepo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kepo.databinding.ActivityProfileBinding;
import com.example.kepo.model.User;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private UserLog userLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        userLog = new UserLog(this);

        User user = userLog.loadLog();

        binding.tvName.setText(user.getName());
        binding.tvUsename.setText(user.getUsername());

        binding.ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder logoutDialog = new AlertDialog.Builder(ProfileActivity.this);

                logoutDialog.setTitle("Logout");
                logoutDialog.setMessage("Are You Sure Want to logout? ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userLog.clearLog();
                                Intent getIntent = new Intent(ProfileActivity.this, splashActivity.class);
                                startActivity(getIntent);
                                finish();
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

            }
        });





    }
}