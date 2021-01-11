package com.example.kepo.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.kepo.BR;

import java.io.Serializable;
import java.util.ArrayList;

public class User extends BaseObservable implements Serializable {

    private String username, password, user_id, name;
    private ArrayList<Todo> myTodo;


    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
        notifyPropertyChanged(BR.user_id);
    }

    @Bindable
    public ArrayList<Todo> getMyTodo() {
        return myTodo;
    }

    public void setMyTodo(ArrayList<Todo> myTodo) {
        this.myTodo = myTodo;
        notifyPropertyChanged(BR.myTodo);
    }

    public int getCountTodo(){
        if(myTodo.size() == 0) return 0;
        return myTodo.size();
    }

    public void AddMyTodo(Todo newTodo){
        this.myTodo.add(newTodo);
    }

    public Todo getTodo(String user_id){
        for(int i = 0; i < myTodo.size(); i++){
            if(myTodo.get(i).getUser_id().equals(user_id)){
                return myTodo.get(i);
            }
        }
        return null;
    }

}
