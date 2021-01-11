package com.example.kepo;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.kepo.model.User;

public class UserLog {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UserLog(Context context){
        sharedPreferences = context.getSharedPreferences("userlog", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveLog(User user){
        editor.putString("user_id", user.getUser_id());
        editor.putString("username", user.getUsername());
        editor.putString("name", user.getName());
        editor.apply();
    }

    public User loadLog(){
        User user = new User();
        user.setUser_id(sharedPreferences.getString("user_id", ""));
        user.setName(sharedPreferences.getString("name", ""));
        user.setUsername(sharedPreferences.getString("username",""));

        return user;
    }

    public void clearLog(){
        editor.clear();
        editor.apply();
    }

}
