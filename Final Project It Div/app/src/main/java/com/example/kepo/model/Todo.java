package com.example.kepo.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.kepo.BR;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo extends BaseObservable implements Serializable {
    private String title, name, todo_id, user_id;
    private String desc;
    private String lastEdited;
    private boolean isChecked;

    @Bindable
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        notifyPropertyChanged(BR.checked);
    }

    public Todo() {
        this.title = "";
        this.name = "";
        this.todo_id = "";
        this.user_id = "";
        this.desc = "";
        this.lastEdited = "";
        this.isChecked = false;
    }

    @Bindable
    public String getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(String todo_id) {
        this.todo_id = todo_id;
        notifyPropertyChanged(BR.todo_id);
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged(BR.desc);
    }

    @Bindable
    public String getLastEdited() {

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy HH:mm");

        Date nowDate = null;
        try
        {
            nowDate = input.parse(lastEdited);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }


        return output.format(nowDate);
    }

    public void setLastEdited(String lastEdited) {
        this.lastEdited = lastEdited;
        notifyPropertyChanged(BR.lastEdited);
    }

}
